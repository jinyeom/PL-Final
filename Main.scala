
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
              << ('a' ==> 'A').
              __ ('b' ==> 'B') >>
            ),
            'B' ==> (
            << ('a' ==> 'A').
            __ ('c' ==> 'C') >>
            ),
            'C' ==> (
            << ('a' ==> 'A').
            __ ('d' ==> 'D') >>
            ),
            'D' ==> (
            << ('a' ==> 'A').
            __ ('b' ==> 'B') >>
            )
        ),
      List('B')
    )
    
    fsm setShouldLogState true
    fsm setLogFileName "test"
    
    println (fsm accept ('A', "abaabcdb" toList))
//    println (fsm2 accept ('B', "ababababab" toList))
//    println (fsm2 accept ('C', "abcab" toList))
//    
    /* Demonstration code ==> no exception handling! */
    val ois = new ObjectInputStream(new FileInputStream(LogUtil.fullFileName("test")))
    val log: FSMLogInfo[Char,Char] = ois.readObject().asInstanceOf[FSMLogInfo[Char,Char]]
    
    println(log.logFileName)
    println(log.states)
    println(log.transitions)
    println(log.acceptStates)
    println(log.visitedStates)
    println(log.inputString)
    
    val vis: FSMVisualizer[Char, Char] = new FSMVisualizer(log.logFileName, log)
    
    
    /* PushDown test */
    val pda = new PushDown(
//			List(
//						("any", 'a', Empty)      ==> ("any", Push('A')),
//						("any", 'a', Head('A'))  ==> ("any", Push('A')),
//						("any", 'b', Head('A'))  ==> ("any", Pop),
//						("any", 'c', Head('A'))  ==> ("any", DoNothing)
//			),
        List(
            ("any", Empty) ==> (
                << ('a' ==> ("any", Push('A'))) >>
            ),
            ("any", Head('A')) ==> (
                << ('a' ==> ("any", Push('A').asInstanceOf[StackOp])).
                __ ('b' ==> ("any", Pop.asInstanceOf[StackOp])).
                __ ('c' ==> ("any", DoNothing.asInstanceOf[StackOp])) >>
            )
        ),
        List.empty // only accept on empty stack
	  )
  }
  
}
