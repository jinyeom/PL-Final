
object Main {

	def main(args: Array[String]) = {

//		val fsm = new FSM[Char, Char](
//				// transitions: state => list of (letter => state)
//				List( 
//						'A' ==> (
//								<< ('a' ==> 'B').
//								__ ('b' ==> 'A') >>
//								),
//						'B' ==> (
//								<< ('a' ==> 'A').
//								__ ('b' ==> 'B') >>
//								)
//						),
//	
//				// accepting state(s)
//				List ('A')
//		)

//		Console println (fsm accept ('A', List('a', 'b', 'a')))
//		
//		val pda = new PushDown(
//			List(
//						("any", 'a', Empty)      ==> ("any", Push('A')),
//						("any", 'a', Head('A'))  ==> ("any", Push('A')),
//						("any", 'b', Head('A'))  ==> ("any", Pop),
//						("any", 'c', Head('A'))  ==> ("any", DoNothing)
//			),
//			
//			List.empty // only accept on empty stack
//		)
//			
//		Console println (pda accept ("any", "aabbaaabbbabaababbb".toList))
		
	}
}
