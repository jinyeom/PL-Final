
package object Automata {

	type TransitionList[S,A] = List[(A,S)]
	
	trait StackOp[+A]
	case class Push[A](a:A) extends StackOp[A]
	case object Pop extends StackOp[Nothing]
	case object DoNothing extends StackOp[Nothing]
	case class PopPush[A](a:A) extends StackOp[A]
	
	trait StackHeadState[+A]
	case class Head[A](a: A) extends StackHeadState[A]
	case object Empty extends StackHeadState[Nothing]


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
	
	implicit def someWrapper[A](a: A) = {
		Some(a)
	}

}

package Automata {

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

	// delta: Q x (S v {empty}) x G => PowerSet(Q x G*) [here we implement only deterministic pda,
	// so this is equivalent to mapping into Q x G* by the equivalence {x} => x 

	// okay but it's bad to implement explicit transitions because
	// that would be a huge space overhead....
	// so we can map into {push(x), pop()} actions on the stack
	// just define an enum PUSH, POP
	// and then have a list that is basically {(PUSH, x in G), POP}
	// that's equivalent to Option[G] in Scala right
	// case classes defined for this woohoo

	// first question is how to simplify the expression of this
	// putting this off
	// first get something working

	// S: state type
	// IA: input alphabet type
	// SA: stack alphabet type
	class PushDown[S, IA, SA]( transitions:List[((S, IA, StackHeadState[SA]), (S, StackOp[SA]))],
															acceptStates: List[S]) {

		val transitionMap = scala.collection.mutable.HashMap.empty[(S,IA,StackHeadState[SA]), (S,StackOp[SA])]
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
				(acceptStates contains initState) || (stack.size == 0)
			}
		}

		def transition(s: S, a: IA) = {
			
			val head: StackHeadState[SA] = if (stack.size > 0) Head(stack.head) else Empty
			
			transitionMap get (s, a, head) match {
				case Some((state, stackop)) => {
					stackop match {
						case Push(stackLetter) => stack.push(stackLetter)
						case Pop => if (stack.size > 0) stack.pop()
						case PopPush(stackLetter) => {
							stack.pop()
							stack.push(stackLetter)
						}
						case DoNothing => Unit
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

		val fsm = new FSM[Char, Char](
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
		
		val pda = new PushDown(
			List(
						("any", 'a', Empty)       ==> ("any", Push('A')),
						("any", 'a', Head('A'))  ==> ("any", Push('A')),
						("any", 'b', Head('A'))  ==> ("any", Pop),
						("any", 'c', Head('A'))  ==> ("any", DoNothing)
			),
			
			List.empty // only accept on empty stack
		)
			
		Console println (pda accept ("any", "aabbaaabbbabaababbb".toList))
		
	}
}

