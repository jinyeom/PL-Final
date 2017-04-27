package automata

import java.io._

class FSM[S, A](transitions: List[(S,List[(A,S)])], acceptStates: List[S]) 
    extends LoggableAutomaton[FSMLogInfo[S,A], S, A]{

  private val transitionMap = scala.collection.mutable.HashMap.empty[S, List[(A,S)]]
  private val states = scala.collection.mutable.ListBuffer.empty[S]
  
  
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
  def makeLog(inputString: List[A]) = {
  	if (shouldLogState) {
  		logFileName match {
  			case Some(fileName) => {
  				Some(
  				  new FSMLogInfo(
              LogUtil.fullFileName(LogUtil.makeFileName(fileName)),
              inputString,
              states toList,
              transitionMap,
              acceptStates)
  				)
  			}
  			case None => None
  		}
    } else {
  	  None
    }
  }

  def recordTransition(s: S) {
  	log match {
  		case Some(fsmLogInfo) => {
  			fsmLogInfo.recordVisited(s)
  		}
  		case None => Unit
  	}
  }
  
}
