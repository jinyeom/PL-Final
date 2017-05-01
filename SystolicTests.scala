
import automata._
import java.io._

object SystolicTests {

  def main(args: Array[String]) = {
    
    /* Just generate 0-4 and then print them out */
    val cell0 = new SystolicCell
    val input0 = new InputCell(
    		<< (0) __ (1) __ (2) __ (3) >>
    )
    
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
    
    val matrix = List(
    		<< (2.0) __ (0.0) __ (0.0) >>,
    		<< (0.0) __ (2.0) __ (0.0) >>,
    		<< (0.0) __ (0.0) __ (2.0) >>
    )
    
    val vector = List(3.0, 3.0, 3.0)
    
    val mbv = new MatrixByVectorMultiplyArray(matrix)
    val (output, valueCells, inputCells, rightin) = mbv.getSystolicArray(vector)
    
    for (i <- 1 to 20){
			output.step
			valueCells map (_.step)
			inputCells map (_.step)
			rightin.step
			output.update
			valueCells map (_.update)
			inputCells map (_.update)
			rightin.update
			output.getOutput match {
				case Some(value) => println(value)
				case None => Unit
			}
    }
  }
  
}
