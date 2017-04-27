package automata

class NonDeterministicPushDown[S, IA, SA](transitions: List[((S, IA, StackHeadState[SA]), List[(S, StackOp[SA])])],
		
		// List[((S, StackHeadState[SA]), List[(IA, (S, StackOp[SA]))]]
    acceptStates: List[S]) {
    
	
	// NDPushDownState will need the input string, the stack, and the current state
	// using list as the stack
	// this means that push will be

	/*
	 * nd._3 match {
	 *     case s::sas => {
	 *       tran
	 *     }
	 *     case _     => 
	 * 
	 * 
	 * 
	 */
	val transitionMap = scala.collection.mutable.HashMap.empty[(S, IA, StackHeadState[SA]), List[(S, StackOp[SA])]]
	
	
}