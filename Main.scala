
import automata._
//import java.io._

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
  	 
  	 Console println (nd accept ('P', "011110101010100101" toList))
  	  
  }
  
}
