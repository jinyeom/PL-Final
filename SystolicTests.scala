
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
    
    
    /* 
     * More complicated example, construction 
     * code in MatrixByVectorMultiply.scala 
     */
    val matrix1 = List(
        << (2.0) __ (0.0) __ (0.0) __ (0.0) >>,
        << (0.0) __ (2.0) __ (0.0) __ (0.0) >>,
        << (0.0) __ (0.0) __ (2.0) __ (0.0) >>,
        << (0.0) __ (0.0) __ (0.0) __ (2.0) >>
    )
    
    val vector1 = List(3.0, 3.0, 3.0, 3.0)
    
    println ("Test1")
    
    val mbvBuilder1 = new MatrixByVectorMultiply(matrix1)
    val mbv1 = mbvBuilder1 getSystolicArray vector1
    
    val matrix2 = List(
        << (2.0) __ (0.0) __ (0.0) >>,
        << (0.0) __ (2.0) __ (0.0) >>,
        << (0.0) __ (0.0) __ (2.0) >>
    )
    
    mbv1 printOutputs
    
    println ("\nTest2")
    
    val vector2 = List(3.0, 3.0, 3.0)
    
    val mbvBuilder2 = new MatrixByVectorMultiply(matrix2)
    val mbv2 = mbvBuilder2 getSystolicArray vector2
    
    mbv2 printOutputs
    
    
  }
  
}
