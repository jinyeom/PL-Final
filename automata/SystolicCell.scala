package automata

class SystolicCell {
  
  private var topInput: Option[SystolicCell] = None
  private var bottomInput: Option[SystolicCell] = None
  private var leftInput: Option[SystolicCell] = None
  private var rightInput: Option[SystolicCell] = None
  
  private var stepFunction:  Option[(Option[Double], Option[Double], Option[Double], Option[Double]) => Option[Double]] = None
  
  var computed: Option[Double] = None
  var output: Option[Double] = None
  
  def update = {
    output = computed
  }
  
  def setRightInput(cell: SystolicCell) = {
    rightInput = Some(cell)
  }
  
  def setTopInput(cell: SystolicCell) = {
    topInput = Some(cell)
  }
  
  def setLeftInput(cell: SystolicCell) = {
    leftInput = Some(cell)
  }
  
  def setBottomInput(cell: SystolicCell) = {
    bottomInput = Some(cell)
  }
  
  def setStepFunction(function: (Option[Double], Option[Double], Option[Double], Option[Double]) => Option[Double]) = {
    stepFunction = Some(function)
  }
  
  private def cellOutput(maybeCell: Option[SystolicCell]) = maybeCell match {
    case Some(cell) => cell output
    case None => None
  }
  
  def step = {
    val List(top, bottom, left, right) = 
      List(topInput, bottomInput, leftInput, rightInput) map cellOutput
    
    computed = stepFunction match {
      case Some(fun) => fun(top, bottom, left, right)
      case None => None
    }
  }
  
  def getOutput = output
}