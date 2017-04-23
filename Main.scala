
import automata._
import java.io._

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
    
    println (fsm accept ('A', "abaa" toList))
    println (fsm accept ('B', "ababababab" toList))
    
    /* Demonstration code ==> no exception handling! */
    val ois = new ObjectInputStream(new FileInputStream(LogUtil.fullFileName("test")))
    val log: FSMLogInfo[Char,Char] = ois.readObject().asInstanceOf[FSMLogInfo[Char,Char]]
    
    println(log.logFileName)
    println(log.states)
    println(log.transitions)
    println(log.acceptStates)
    println(log.inputString)
  }
  
}
