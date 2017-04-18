package automata

class FSM[S, A](transitions: List[(S,TransitionList[S,A])], acceptStates: List[S]) {

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