
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
      List('B', 'A')
    )
    
    fsm setShouldLogState true
    fsm setLogFileName "test"
    
    Console println (fsm accept ('A', "abaa" toList))
    Console println (fsm accept ('B', "ababababab" toList))
  }
  
}
