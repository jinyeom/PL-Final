package automata

import java.io._

class FSM[S, A](transitions: List[(S,List[(A,S)])], acceptStates: List[S]) {
  
    private val transitionMap = scala.collection.mutable.HashMap.empty[S, List[(A,S)]]
    private val states = scala.collection.mutable.ListBuffer.empty[S]
    
    /* Logging information */
    private var shouldLogState = false
    private var logFileOpen = false
    private var log: FSMLogInfo[S,A] = null
    private var logFileName: String = null
    
    /* Build the transition map */
    transitions map (p => 
      transitionMap += (p._1 -> p._2)
    )
    
    /* Build the state list for the log potentially */
    transitionMap.keySet foreach (states += _)

    def accept(initState: S, seq: List[A]) : Boolean = {
      
      /* Write the state to the log or set it up if necessary */
      updateLog(initState, seq)
      
      if (seq.length > 0){
        transition (initState, seq.head) match {
          case Some(nextState) => accept(nextState, seq.tail)
          case None => false
        }
      } else {
        /* Clean up the logging data */
        writeLog
        cleanUpLog

        acceptStates contains initState
      }
    }

    def transition(s : S, a : A) = transitionMap get s match {
      case Some(transitionList) => {
        val transitions = transitionList filter (_._1 == a)
        if ((transitions length) == 0) {
          None
        } else {
          Some(transitions.head _2)
        }
      }
      case None => None
    }
    
    /* Logging functions */
    
    /**
     * Setup code for the log file
     */
    private def updateLog(s: S, input: List[A]) {      
      if (!logFileOpen)
        setupLog(input)
      writeState(s)
    }
    
    private def setupLog(inputString: List[A]) = {
      if (shouldLogState) {
        log = new FSMLogInfo(
            LogUtil.fullFileName(LogUtil.makeFileName(logFileName)),
            inputString,
            states toList,
            transitionMap,
            acceptStates
          )
        logFileOpen = true
      }
    }
    
    private def writeState(s: S) = {
      if (logFileOpen)
        log.recordVisited(s)
    }
    
    private def writeLog() = {
      if (logFileOpen)
        log.writeToLogFile()
    }
    
    private def cleanUpLog() = {
      log = null
      logFileOpen = false
    }
    
    /**
     * Set the name of the file to log to fileName
     * Sets shouldLogState to true
     */
    def setLogFileName(fileName: String) = {
      logFileName = fileName
    }
    
    /**
     * Set whether this automaton should log state
     */
    def setShouldLogState(b: Boolean) = {
      shouldLogState = b
    }
    
    /**
     * Get value of shouldLogState
     */
    def willLog = shouldLogState
    
    /**
     * Get name of file that the log will
     * be written to
     */
    def loggingToFile = logFileName

}
