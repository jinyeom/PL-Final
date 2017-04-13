
package object Automata {
  
  type TransitionList[S,A] = List[(A,S)]
  
  trait StackOp[+A]
  case class Push[A](a:A) extends StackOp[A]
  case object Pop extends StackOp[Nothing]
  case class PopPush[A](a:A) extends StackOp[A]
  
  
  // constructs a pair
	implicit class Transition[S,A](a: A) {
	  def ==> (s: S) = (a, s)
  }

  // alternate list constructor
  object << {
    def apply[A](a: A) = {
      List[A](a)
    }
  } 
  
  // not necessary but it makes things look much nicer
  // just a list identity function
  implicit class EndList[A](l:List[A]) {
    def >> = l
  }
  
  // fancy append operator
  implicit class AppendOperation[A](l:List[A]) {
    def __ (a:A) = l ++ List(a)
  }
  
}

package Automata {

  class FSM[S, A](alphabet: List[A], transitions: List[(S,TransitionList[S,A])], acceptStates: List[S]) {
    
    val transitionMap = scala.collection.mutable.HashMap.empty[S, TransitionList[S,A]];
    
    transitions map (p => 
      transitionMap += (p._1 -> p._2)
    )
    
    def accept(initState: S, seq: List[A]) : Boolean = {
      if ((seq length) > 0){
        transition (initState, seq.head) match {
          case Some(nextState) => accept(nextState, seq.tail)
          case None => false
        }
      } else {
        acceptStates contains initState
      }
    }
    
    def transition(s : S, a : A) = {
      transitionMap get s match {
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
    
  }
  
  // delta: Q x (S v {empty}) x G => PowerSet(Q x G*) [here we implement only deterministic pda,
  // so this is equivalent to mapping into Q x G* by the equivalence {x} => x 
  
  // okay but it's bad to implement explicit transitions because
  // that would be a huge space overhead....
  // so we can map into {push(x), pop()} actions on the stack
  // just define an enum PUSH, POP
  // and then have a list that is basically {(PUSH, x in G), POP}
  // that's equivalent to Option[G] in Scala right
  
  // first question is how to simplify the expression of this
  // putting this off
  // first get something working
  
  // S: state type
  // IA: input alphabet type
  // SA: stack alphabet type
  class PushDown[S, IA, SA](inputAlphabet: List[IA], 
                            transitions:List[((S, IA, SA), (S, StackOp[SA]))],
                            acceptStates: List[S]) {
    
    val transitionMap = scala.collection.mutable.HashMap.empty[(S,IA,SA), (S,StackOp[SA])]
    transitions map (p =>
      transitionMap += (p._1 -> p._2)
    )
    
    val stack = scala.collection.mutable.Stack.empty[SA]
    
    def accept(initState: S, seq: List[IA]) : Boolean = {
      if (seq.length > 0){
        transition (initState, seq.head) match {
          case Some(nextState) => accept(nextState, seq.tail)
          case None => false
        }
      } else {
        acceptStates contains initState
      }
    }
    
    def transition(s: S, a: IA) = {
      transitionMap get (s, a, stack.head) match {
        case Some((state, stackop)) => {
          stackop match {
            case Push(stackLetter) => stack.push(stackLetter)
            case Pop => stack.pop()
            case PopPush(stackLetter) => {
              stack.pop()
              stack.push(stackLetter)
            }
          }
          Some(state)
        }
        case None => None
      }
    }
    
  }

}

object Main {
  import Automata._

  def main(args: Array[String]) = {
    
    // some examples
    
    val L = << ('a' ==> 'b').
            __ ('b' ==> 'c').
            __ ('c' ==> 'd').
            __ ('d' ==> 'e') >>
    
    val M = << (3).
            __ (4).
            __ (5) >>
    
    val otherList = List(
        'A' ==> (
            << ('b' ==> 'C').
            __ ('c' ==> 'D') >>
        ),
        'B' ==> (
            << ('a' ==> 'C').
            __ ('b' ==> 'D') >>
        )
   )      
    
    Console println ( << ('A' ==> 'B').
                      __ ('B' ==> 'A')>> )
    
    Console println otherList
    
    val fsm = new FSM[Char, Char](
        
        // alphabet
        List('a', 'b'),
        
        // transitions: state => list of (letter => state)
        List( 
            'A' ==> (
              << ('a' ==> 'B').
              __ ('b' ==> 'A') >>
              ),
            'B' ==> (
            << ('a' ==> 'A').
            __ ('b' ==> 'B') >>
              )
        ),
        
        // accepting state(s)
        List ('A')
    )
    
    Console println (fsm accept ('A', List('a', 'b', 'a')))
  }
}

