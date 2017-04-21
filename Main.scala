
import automata._

object Main {

	def main(args: Array[String]) = {
		val fsm = new FSM[Char,Char](
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
			List('B')
		)
		
		fsm.setShouldLogState(true)
		Console println (fsm.loggingToFile)
		
		Console println (fsm accept ('A', "abaa" toList))
	}
}
