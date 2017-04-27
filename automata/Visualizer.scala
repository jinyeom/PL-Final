package automata

import java.awt.geom._
import javax.swing._

import scala.collection.mutable.HashMap

// Visualizer given two types for input string and states, and a LogInfo.
class Visualizer[A, B](log: LogInfo) extends JFrame {
  
  // log type check and cast
  //
  //  TODO: Check with Max about PushDownInfo having three types, instead of two.
  //
  def logCast(log: LogInfo) = log match {
    case _: FSMLogInfo[A, B] => log.asInstanceOf[FSMLogInfo[A, B]]
    // case _: NonDeterministicFSMLogInfo[A, B] => log.asInstanceOf[NonDeterministicFSMLogInfo[A, B]]
  }
  
  var _log = logCast(log)             // _log is a casted version of LogInfo.
  var currState = _log.states().head  // currState points to the current state.
  
  // initialize window
  setTitle("Visual Automata")
  setSize(300, 200)
  setLocationRelativeTo(null)
  
  // update
  def update() {
    
    // TODO: Essentially, move the currState to the next state
    
  }
  
  // draw
  def draw() {
    
    // TODO: Draw the current states
    
  }
  
  // start the animation.
  def start() {
    setVisible(true)
  }
}
