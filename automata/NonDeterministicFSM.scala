package automata

import scala.concurrent._
import ExecutionContext.Implicits.global

/* 
 * FSM with a transition relation rather than function
 * and a parallelized accept function
 */

class NonDeterministicFSM[S, A](transitions: List[(S,List[(A,List[S])])], acceptStates: List[S]) {
	
  private val transitionMap = scala.collection.mutable.HashMap.empty[S, List[(A,List[S])]]
  private val states = scala.collection.mutable.ListBuffer.empty[S]
	
  transitions map (p => 
    transitionMap += (p._1 -> p._2)
  )
  
  transitionMap.keySet map (states += _)
  
	/*
	 * Accept function
	 * Something, something, List[Future[Boolean]]?
	 * fold (||) false <result>
	 */
	
	/*
	 * State will not be in the object itself, but passed around as
	 * an NDState object (basically a Tuple2[S, List[A]] representing
	 * the state along with the remaining input string.
	 * 
	 * (a: A, list: List[S])
	 * list map (s: S => (s, inputString)) map (accept _)
	 * right?
	 */
	
	def accept(initState: S, seq: List[A]) = {
		acceptHelper(new NDState[S,A](initState, seq))
	}
	
	private def acceptHelper(ndState: NDState[S,A]) = {
		val currentState: S = ndState._1
		val inputString: List[A] = ndState._2
		
		if (inputString.length > 0) {
			transition(currentState, inputString.head) match {
				case Some(stateList) => {
					/*
					 * Do some future-y stuff
					 */
					stateList map (new NDState(_, inputString.tail)) // map futureFun foldLeft false (||)
				}
				case None => false
			}
		} else {
			acceptStates contains currentState
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
