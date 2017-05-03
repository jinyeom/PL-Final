package automata

import scala.collection.mutable.{Seq => MutableSeq}

class MatrixByVectorMultiply(matrix: List[List[Double]]) {
  val lengthsEqualList = matrix map (_.length) map (_ == matrix.head.length)
  val validMatrix = lengthsEqualList.foldLeft(true)((a, b) => a && b)
  if (!validMatrix) 
    throw new IllegalArgumentException("matrix rows not of equal length")
  
  private var inputQueues: MutableSeq[List[Double]] = {
    var seq = MutableSeq.empty[List[Double]]
    matrix foreach {
      _ => seq = seq :+ List.empty
    }
    seq
  }
      
  /* Append zeros properly to the input queues*/
  for (i <- 0 until matrix.length) {
    (1 until (matrix.length - i)) foreach { 
      _ => inputQueues.update(i, 0 :: inputQueues(i)) 
    }
  }
  
  matrix foreach {
    row => {
      var tempList = row
      
      for (i <- 0 until row.length) {
        inputQueues.update(i, inputQueues(i) ++ List(tempList.head))
        tempList = tempList.tail
      }
      
      (0 until row.length) foreach { 
        i => inputQueues.update(i, inputQueues(i) ++ List(0.toDouble))
      }
    }
  }
  
  def getSystolicArray(vector: List[Double]) = {
    
    if (vector.length != matrix.head.length)
      throw new IllegalArgumentException
    
    val valueCells = vector map (new MBVCell(_))
    val inputCells = inputQueues.toList map (new InputCell(_))
    val outputCell = new SystolicCell
    
    outputCell setStepFunction (
      (t, b, l, r) => r
    )
    
    (valueCells zip inputCells) foreach {
      pair => pair._1 setTopInput pair._2
    }
    
    (valueCells zip valueCells.tail) map (pair => pair._1 setRightInput pair._2)
    
    
    val rightin = new MBVRightInputCell(matrix.length * 5 + 1)
    outputCell setRightInput valueCells.head
    valueCells.reverse.head setRightInput (rightin)
    
    new MBVArray(matrix, outputCell, rightin :: valueCells ++ inputCells)
  }
}

class MBVArray[T <: SystolicCell](matrix: List[List[Double]],
  outputCell: SystolicCell, 
  array: List[T]) 
  extends SystolicArray(outputCell :: array) {
  
  private val stepsBeforeOutput = matrix.head.length + 2
  
  def getOutput = outputCell getOutput
  
  def printOutputs {
    (1 to stepsBeforeOutput) foreach { s => fullStep }
    while (getOutput != None) {
      getOutput match {
        case Some(v) => println(v)
        case None => Unit
      }
      fullStep
    }
  }
  
}

class MBVCell(value: Double) extends SystolicCell {
  
  setStepFunction(
      (t, b, l, r) => {
        (t, r) match {
          case (Some(tval), Some(rval)) => {
            Some(tval*value + rval)
          }
        case _ => None
      }
    }
  )
  
}

class MBVRightInputCell(num: Int)
extends InputCell(List.fill(num)(0.toDouble))