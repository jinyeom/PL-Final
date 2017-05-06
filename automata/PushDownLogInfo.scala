package automata

import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Stack

@SerialVersionUID(222)
class PushDownLogInfo[S, IA, SA](
    logFileName: String,
    inputString: List[IA],
    states: List[S],
    transitions: HashMap[(S, StackHeadState[SA]), List[(IA, (S, StackOp))]],
    acceptStates: List[S])
  extends LogInfo(logFileName)
  with Serializable {
	
  private var statesAndStackContents = ListBuffer.empty[(S, List[SA])]
  
	def logFileName(): String = logFileName
  def inputString(): List[IA] = inputString
  def states(): List[S] = states
  def transitions(): HashMap[(S, StackHeadState[SA]), List[(IA, (S, StackOp))]] = transitions
  def acceptStates(): List[S] = acceptStates

  def visitedStatesAndStacks() = statesAndStackContents.toList
  
  /* Record that a state was visited */
  def recordVisited(stateAndStack: (S, List[SA])) = {
    statesAndStackContents += stateAndStack
  }
  
}