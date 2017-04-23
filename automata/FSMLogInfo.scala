package automata

import scala.collection.mutable.ListBuffer

@SerialVersionUID(111L) // what does this even do
class FSMLogInfo[S,A](
		logFileName: String,
		inputString: List[A],
		states: List[S],
		acceptStates: List[S]) 
	extends Serializable{
  
  /* States that the FSM visited during the run */
  var statesVisitedDuringRun: ListBuffer[S] = ListBuffer.empty
  
  /* Record that a state was visited */
  def recordVisited(state: S) = {
  	statesVisitedDuringRun += state
  }
 
}