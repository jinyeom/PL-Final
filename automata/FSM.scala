package automata

import java.io._

class FSM[S, A](transitions: List[(S,List[(A,S)])], acceptStates: List[S]) {
  
    private val transitionMap = scala.collection.mutable.HashMap.empty[S, List[(A,S)]]
    
    /* Logging utilities */
    private var shouldLogState = false
    private var logFileOpen = false
    
    private var logFileName: String = null
    private var usedLogFile: String = null
    
    private var fileOutput: PrintWriter = null;

    transitions map (p => 
      transitionMap += (p._1 -> p._2)
    )

    def accept(initState: S, seq: List[A]) : Boolean = {
      
      /* Write the state to the log or set it up if necessary */
      updateLog(initState)
      
      if (seq.length > 0){
        transition (initState, seq.head) match {
          case Some(nextState) => accept(nextState, seq.tail)
          case None => false
        }
      } else {
        /* Clean up the logging data */
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
    private def updateLog(s: S) {      
      if (!logFileOpen)
        setupLog()
      writeState(s)
    }
    
    private def setupLog() = {
      if (shouldLogState) {
        usedLogFile =  LogUtil.fullFileName(LogUtil.makeFileName(logFileName))
        
        val logFile = new File(usedLogFile)
        logFile.getParentFile.mkdirs
        logFile.createNewFile
        
        fileOutput = new PrintWriter(usedLogFile)
        fileOutput.print(logHeader)
        logFileOpen = true
      }
    }
    
    private def writeState(s: S) = {
      if (logFileOpen)
        fileOutput.print(logState(s))
    }
    
    private def cleanUpLog() {
      usedLogFile = null
      fileOutput.close()
      fileOutput = null
      logFileOpen = false
    }
    
    /**
     * Set the name of the file to log to fileName
     * Sets shouldLogState to true
     */
    def setLogFileName(fileName: String) = {
      /* Shouldn't even be logging */
      logFileName = fileName
    }
    
    /**
     * Set whether this automaton should log state
     * Also this code should probably be reused
     * Since it's gonna be annoying to rewrite
     * TODO generalize when done
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
    
    /**
     * Dump the transition map
     */
    private def logHeader() = {
      val foldFun = (acc: String, next: Any) =>  acc + next.toString
      
      "transitions:" + transitionMap.foldLeft("")(foldFun) + "\n" +
      "accept:" + acceptStates + "\n"
    }
    
    /**
     * Dump the current state 
     */
    private def logState(currentState: S) = {
      "state: " + currentState.toString + "\n"
    }

  }