package automata

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class NonDeterministicPushDown[S, IA, SA](transitions: List[((S, StackHeadState[SA]), List[(IA, List[(S, StackOp)])])],
    acceptStates: List[S]) {
    
  
  // NDPushDownState will need the input string, the stack, and the current state
  // using list as the stack

  val transitionMap = scala.collection.mutable.HashMap.empty[(S, StackHeadState[SA]), List[(IA, List[(S, StackOp)])]]
  transitions map (p =>
    transitionMap += p._1 -> p._2
  )
  
  def accept(initState: S, inputString: List[IA]) = {
    acceptHelper(new NDPushDownState(initState, inputString, List.empty))
  }
  
  private def acceptHelper(ndState: NDPushDownState[S, IA, SA]): Boolean = {
    val (currentState, inputString, stack) = ndState
    
    if (inputString.length > 0) {
      transition(currentState, inputString.head, stack) match {
        case Some(stateAndOpList) => {
          val statesAndStacks = stateAndOpList map (p => (p._1, performStackOp(stack, p._2)))
          val ndStatesReachable = statesAndStacks map (p => new NDPushDownState(p._1, inputString.tail, p._2))
          
          (ndStatesReachable map futureAccept).
            foldLeft(false)((acc: Boolean, next: Future[Boolean]) => futureOr(acc, next))
        }
        case None => false
      }
    } else {
      acceptStates contains currentState
    }
  }
  
  private def performStackOp(stack: List[SA], stackop: StackOp) = {
    stackop match {
      case Push(stackLetter: SA) => stackLetter :: stack
      case Pop => stack match {
        case a :: as => as
        case Nil => Nil
      }
      case PopPush(stackLetter: SA) => stack match {
        case a :: as => stackLetter :: as
        case Nil => stackLetter :: stack
      }
    }
  }
  
  private def futureAccept(ndState: NDPushDownState[S, IA, SA]) = {
    Future {
      acceptHelper(ndState)
    }
  }
  
  def transition(s: S, ia: IA, stack: List[SA]) = {
    val head: StackHeadState[SA] = if (stack.size > 0) Head(stack.head) else Empty
    
    transitionMap get (s, head) match {
      case Some(transitionList) => {
        val possibleTransitions = transitionList filter (ia == _._1)
        if (possibleTransitions.length > 0) {
          // will need to perform operations on the stacks during accept
          Some(possibleTransitions.head._2)
        } else {
          None
        }
      }
      case None => None
    }
  }
}