
import automata._
import java.io._

object Main {

  def main(args: Array[String]) = {
    
//	  /* Just generate 0-4 and then print them out */
//  	val cell0 = new SystolicCell
//  	val input0 = new InputCell(<< (0) __ (1) __ (2) __ (3) >>)
//  	
//  	cell0 setRightInput input0
//  	cell0 setStepFunction (
//  			(top, bottom, left, right) => right
//  		)
//  	
//  	input0.step
//  	cell0.step
//  	input0.update
//  	cell0.update
//  	
//  	while (input0.getOutput != None) {
//  		input0.step
//  		cell0.step
//  		input0.update
//  		cell0.update
//  	}
//  	
//  	println(input0 getOutput) // None
//  	println(cell0 getOutput)  // Some(3.0)
  	
  	val fsm = new FSM[Char,Char](
      List(
          'A' ==> (
            << ('a' ==> 'B').
            __ ('c' ==> 'C').
            __ ('b' ==> 'A') >>
          ),
          'B' ==> (
            << ('a' ==> 'A').
            __ ('c' ==> 'C').
            __ ('b' ==> 'B') >>
          ),
          'C' ==> (
            << ('a' ==> 'A').
            __ ('b' ==> 'B').
            __ ('c' ==> 'C') >>
          )
        ),
      List('B', 'C')
    )
    
    fsm setShouldLogState true
    fsm setLogFileName "test"
    
//    println (fsm accept ('A', "abaa" toList))
//    println (fsm accept ('B', "ababababab" toList))
    println (fsm accept ('A', "abaacabaccbab" toList))
    
    /* Demonstration code ==> no exception handling! */
    val ois = new ObjectInputStream(new FileInputStream(LogUtil.fullFileName("test")))
    val log: FSMLogInfo[Char,Char] = ois.readObject().asInstanceOf[FSMLogInfo[Char,Char]]
    
    println(log.logFileName)
    println(log.states)
    println(log.transitions)
    println(log.acceptStates)
    println(log.inputString)
    println(log.visitedStates())
    
    val vis: FSMVisualizer[Char, Char] = new FSMVisualizer("test", log)
  }
  
}
