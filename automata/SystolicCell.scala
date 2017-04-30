package automata

class SystolicCell(stepFunction: (Option[Double], Option[Double], Option[Double], Option[Double]) => Option[Double]) {
	
	private var topInput: Option[SystolicCell] = None
	private var bottomInput: Option[SystolicCell] = None
	private var leftInput: Option[SystolicCell] = None
	private var rightInput: Option[SystolicCell] = None
	
	private var output: Option[Double] = None
	
	def stepOutput: Option[Double] = {
		output
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
	
	private def cellOutput(maybeCell: Option[SystolicCell]) = maybeCell match {
		case Some(cell) => cell output
		case None => None
	}
	
	def step = {
		val List(top, bottom, left, right) = 
			List(topInput, bottomInput, leftInput, rightInput) map cellOutput
		
		output = stepFunction(top, bottom, left, right)
	}
}