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
	extends Serializable {
	
	def logFileName(): String = logFileName
	def inputString(): List[A] = inputString
	def states(): List[S] = states
	def transitions(): HashMap[S,List[(A,S)]] = transitions
	def acceptStates(): List[S] = acceptStates
	
  /* States that the FSM visited during the run */
  private var statesVisitedDuringRun: ListBuffer[S] = ListBuffer.empty
  
  /* Record that a state was visited */
  def recordVisited(state: S) = {
  	statesVisitedDuringRun += state
  }
  
  /* Create the file and serialize this object */
  @throws(classOf[IOException])
  @throws(classOf[IllegalStateException])
  def writeToLogFile() = {
  	val logFile = new File(logFileName)
  	logFile.getParentFile.mkdirs
  	
  	/* This should not happen if the name generation is correct */
  	if (!logFile.createNewFile)
  		throw new IllegalStateException("File creation failed due to existing file.")
  	
  	println("writing out to " + logFileName + "...")
  	try {
  		val objOutputStream = new ObjectOutputStream(new FileOutputStream(logFileName))
  		objOutputStream writeObject this
  		objOutputStream close
  	} catch {
  		/* Maybe this could be handled more gracefully */
  		case e: IOException => {
  			println("Writing to log " + logFileName + " failed.")
  			e printStackTrace
  		}
  	}
  }
}