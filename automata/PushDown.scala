package automata

// S: state type
// IA: input alphabet type
// SA: stack alphabet type
class PushDown[S, IA, SA](transitions: List[((S, IA, StackHeadState[SA]), (S, StackOp[SA]))],
    acceptStates: List[S]) {

  val transitionMap = scala.collection.mutable.HashMap.empty[(S, IA, StackHeadState[SA]), (S, StackOp[SA])]
  transitions map (p =>
    transitionMap += (p._1 -> p._2))

  val stack = scala.collection.mutable.Stack.empty[SA]

  def accept(initState: S, seq: List[IA]): Boolean = {
    if (seq.length > 0) {
      transition(initState, seq.head) match {
        case Some(nextState) => accept(nextState, seq.tail)
        case None => false
      }
    } else {
      (acceptStates contains initState) || (stack.size == 0)
    }
  }

  def transition(s: S, a: IA) = {

    val head: StackHeadState[SA] = if (stack.size > 0) Head(stack.head) else Empty

    transitionMap get (s, a, head) match {
      case Some((state, stackop)) => {
        stackop match {
          case Push(stackLetter) => stack.push(stackLetter)
          case Pop => if (stack.size > 0) stack.pop()
          case PopPush(stackLetter) => {
            stack.pop()
            stack.push(stackLetter)
          }
          case DoNothing => Unit
        }
        Some(state)
      }
      case None => None
    }
  }
}
