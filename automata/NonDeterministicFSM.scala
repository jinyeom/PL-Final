package automata

import scala.concurrent._
import ExecutionContext.Implicits.global

/* 
 * FSM with a transition relation rather than function
 * and a parallelized accept function
 */

class NonDeterministicFSM[S, A](transitions: List[(S,List[(A,List[S])])], acceptStates: List[S]) {
	
	/*
	 * Accept function
	 * Something, something, List[Future[Boolean]]?
	 * fold (||) false <result>
	 */
	
}
