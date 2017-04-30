package automata

// S: state type
// IA: input alphabet type
// SA: stack alphabet type
class PushDown[S, IA, SA](transitions: List[((S, StackHeadState[SA]), List[(IA, (S, StackOp[SA]))])],
    acceptStates: List[S])
    extends LoggableAutomaton[PushDownLogInfo[S, IA, SA], (S, List[SA]), IA]{

  val transitionMap = scala.collection.mutable.HashMap.empty[(S, StackHeadState[SA]), List[(IA, (S, StackOp[SA]))]]
  val states = scala.collection.mutable.ListBuffer.empty[S]
  
  transitions map (p =>
    transitionMap += (p._1 -> p._2))

  transitionMap.keySet map (states += _._1)
  
  val stack = scala.collection.mutable.Stack.empty[SA]

  def accept(initState: S, seq: List[IA]): Boolean = {
    updateLog((initState, stack toList), seq)
    if (seq.length > 0) {
      transition(initState, seq.head) match {
        case Some(nextState) => accept(nextState, seq.tail)
        case None => false
      }
    } else {
      writeLog
      cleanUpLog
      
      (acceptStates contains initState) || (stack.size == 0)
    }
  }

  def transition(s: S, a: IA) = {

    val head: StackHeadState[SA] = if (stack.size > 0) Head(stack.head) else Empty
    
    transitionMap get (s, head) match {
      case Some(transitionList) => {
        val possibleTransitions = transitionList filter (_._1 == a)
        if (possibleTransitions.length == 0) {
          None
        } else {
          val (state, stackop) = possibleTransitions.head._2
          stackop match {
            case Push(stackLetter) => stack.push(stackLetter)
            case Pop => if (stack.size > 0) stack.pop()
            case PopPush(stackLetter) => {
              if (stack.size > 0) stack.pop()
              stack.push(stackLetter)
            }
            case DoNothing => Unit
          }
          Some(state)
        }
      }
      case None => None
    }
  }
  
  def makeLog(inputString: List[IA]) = {
    if (shouldLogState) {
      logFileName match {
        case Some(fileName) => {
          Some(
            new PushDownLogInfo(
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
  
  def recordTransition(stateAndStack: (S, List[SA])) {
    log match {
      case Some(logInfo) => logInfo.recordVisited(stateAndStack)
      case None => Unit
    }
  }
  
}
