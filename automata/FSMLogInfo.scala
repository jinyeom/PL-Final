package automata

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.HashMap
import scala.collection.immutable.List

import java.io._

/**
 * Class used to record info for an FSM
 */

@SerialVersionUID(111L) // what does this even do
class FSMLogInfo[S,A](
    logFileName: String,
    inputString: List[A],
    states: List[S],
    transitions: HashMap[S,List[(A,S)]],
    acceptStates: List[S]) 
  extends LogInfo(logFileName)
  with Serializable {
  
  /* States that the FSM visited during the run */
  private var statesVisitedDuringRun: ListBuffer[S] = ListBuffer.empty
  
  def logFileName(): String = logFileName
  def inputString(): List[A] = inputString
  def states(): List[S] = states
  def transitions(): HashMap[S,List[(A,S)]] = transitions
  def acceptStates(): List[S] = acceptStates
  
  def visitedStates() = statesVisitedDuringRun
  
  /* Record that a state was visited */
  def recordVisited(state: S) = {
    statesVisitedDuringRun += state
  }
  
}
