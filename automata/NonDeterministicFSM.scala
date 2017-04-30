package automata

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.DurationInt
import scala.util.{Try, Success, Failure} 

class NonDeterministicFSM[S, A](transitions: List[(S,List[(A,List[S])])], acceptStates: List[S]) {
  
  private val transitionMap = scala.collection.mutable.HashMap.empty[S, List[(A,List[S])]]
  private val states = scala.collection.mutable.ListBuffer.empty[S]
  
  transitions map (p => 
    transitionMap += (p._1 -> p._2)
  )
  
  transitionMap.keySet map (states += _)
  
  def accept(initState: S, seq: List[A]) = {
    acceptHelper(new NDFSMState[S,A](initState, seq))
  }
  
  private def acceptHelper(ndState: NDFSMState[S,A]): Boolean = {
  	
  	val (currentState, inputString) = ndState
    
    if (inputString.length > 0) {
      transition(currentState, inputString.head) match {
        case Some(stateList) => {
          
          val ndStatesReachable = stateList map (new NDFSMState(_, inputString.tail))
          
          /* Evaluate all possible paths and fold them up */
          (ndStatesReachable map futureAccept).
            foldLeft(false)((acc: Boolean, next: Future[Boolean]) => futureOr(acc, next))
        }
        case None => false
      }
    } else {
      acceptStates contains currentState
    }
  }

  private def futureAccept(ndState: NDFSMState[S,A]) = {
  	Future {
  		acceptHelper(ndState)
  	}
  }
  
  private def transition(s : S, a : A) = transitionMap get s match {
    case Some(transitionList) => {
      val transitions = transitionList filter (_._1 == a)
      if ((transitions length) == 0) {
        None
      } else {
        Some(transitions.head _2)
      }
    }
    case None => None
  }
}
