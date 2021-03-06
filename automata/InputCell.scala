package automata

class InputCell(var inputQueue: List[Double]) extends SystolicCell {
	
	/* Just generate outputs from the list; these will only be on borders */
	private def stepfun (top: Option[Double], bottom: Option[Double], left: Option[Double], right: Option[Double]) = {
		if (inputQueue.length > 0) {
			val temp = Some(inputQueue.head)
			inputQueue = inputQueue.tail
			temp
		} else {
			None
		}
	}
	
	def getQueue(): List[Double] = inputQueue
	
	setStepFunction(stepfun)
}