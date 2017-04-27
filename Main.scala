
import automata._
import java.io._

object Main {

  def main(args: Array[String]) = {
    
    val nd: NonDeterministicFSM[Char, Char] = new NonDeterministicFSM[Char, Char](
        List(
            'P' ==> (
                << ('0' ==> (
                    << ('P') >>
                  )
                ).
                __ ('1' ==> (
                    << ('P') __ ('Q') >>
                   )
                ) >>
              ),
            'Q' ==> (
                << (NOTHING) >>
            )
        ),
        << ('Q') >>
     )
     
    println (nd accept ('P', "011110101010100101" toList))
    
    val fsmtrans =
      << (('p') ==> (
          << ('1' ==> 'p').
          __ ('0' ==> 'q') >>
         )).
      __ (('q') ==> (
          << ('1' ==> 'p').
          __ ('0' ==> 'q') >>
          )
      ) >>
      
    val x = 'n' ==> ('a', Pop)
    
    val pdtrans = List(
    				('p', Empty) ==> List(
    						('a' ==> ('p', Push('a')))
    				  ),
    				('p', Head('a')) ==> List(
    						('a' ==> ('p', Push('a'))),
    						('b' ==> ('p', Pop))
    					))
    
    val pd = new PushDown[Char, Char, Char](
    		List(
    				('p', Empty) ==> List(
    						('a' ==> ('p', Push('a')))
    				  ),
    				('p', Head('a')) ==> List(
    						('a' ==> ('p', Push('a'))),
    						('b' ==> ('p', Pop))
    					)
    				),
    		List.empty
    )
    
    val fsm = new FSM[Char,Char](fsmtrans, List('p'))
    
    fsm.setShouldLogState(true)
    fsm.setLogFileName("fsm_")
    
    pd setShouldLogState true
    pd setLogFileName "pd_"
    
    println (fsm accept ('p', "00000001110100100101001011" toList))
    println (pd accept ('p', "abababababab" toList))
    
    val readLog = (new ObjectInputStream(new FileInputStream(LogUtil.fullFileName("pd_")))).readObject().asInstanceOf[PushDownLogInfo[Char, Char, Char]]
    
    println(readLog.states())
    println(readLog.visitedStatesAndStacks())
    
  }
  
}
