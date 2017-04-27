package automata

import scala.collection.mutable.ListBuffer

trait LoggableAutomaton[L <: LogInfo, T, A]{
  /* Logging information */
    private var logStateFlag = false
    private var logObject: Option[L] = None
    private var fileName: Option[String] = None
    private var visited: ListBuffer[T] = ListBuffer.empty
    
    def makeLog(input: List[A]): Option[L]

    def recordTransition(t: T): Unit
    
    def logFileName(): Option[String] = fileName
    
    def log(): Option[L] = logObject
    
    def shouldLogState(): Boolean = {
      logStateFlag
    }
    
    def setShouldLogState(b: Boolean) = {
      logStateFlag = b
    }
    
    def setLogFileName(s: String) = {
      fileName = Some(s)
    }
    
    
    def initLog(input: List[A]) {
      logObject = makeLog(input)
    }
    
    def cleanUpLog = {
      logObject = None
    }
    
    def updateLog(t: T, input: List[A]) = logObject match {
      case Some(_) => {
        recordTransition(t)
      }
      case None => initLog(input)
    }
    
    def writeState(t: T) = logObject match {
      case Some(_) => visited += t
      case None => Unit
    }
    
    def writeLog = logObject match {
      case Some(logInfo) => logInfo.writeToLogFile()
      case None => Unit
    }
}