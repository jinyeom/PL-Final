package automata

import java.awt.geom._
import javax.swing._

import scala.collection.mutable.HashMap

// FSMVisualizer given two types for input string and states, and a LogInfo.
class FSMVisualizer[S, A](fsmLog: FSMLogInfo[S, A]) {
  
  var log = fsmLog                                  // _log is a casted version of LogInfo.
  var currState = fsmLog.states().head              // currState points to the current state.
  var frame: JFrame = new JFrame("FSM Visualizer")  // frame for visualization
  var label: JLabel = new JLabel("meh")             // label
  
  def show() { 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.getContentPane.add(label)
    frame.setSize(800, 500)
    
    frame.setVisible(true)
  }
}
