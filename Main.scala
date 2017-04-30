
import automata._
import java.io._

object Main {

  def main(args: Array[String]) = {
    
	  /* Just generate 0-4 and then print them out */
  	val cell0 = new SystolicCell
  	val input0 = new InputCell(<< (0) __ (1) __ (2) __ (3) >>)
  	
  	cell0 setRightInput input0
  	cell0 setStepFunction (
  			(top, bottom, left, right) => right
  		)
  	
  	input0.step
  	cell0.step
  	input0.update
  	cell0.update
  	
  	while (input0.getOutput != None) {
  		input0.step
  		cell0.step
  		input0.update
  		cell0.update
  	}
  	
  	println(input0 getOutput) // None
  	println(cell0 getOutput)  // Some(3.0)
    
  }
  
}
