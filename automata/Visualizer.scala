package automata

import java.awt.geom._
import javax.swing._;

class Visualizer[A, B](log: LogInfo) extends JFrame {
  
  // log type check and cast
  //
  //  TODO: Check with Max about PushDownInfo having three types, instead of two.
  //
  def logCast(log: LogInfo) = log match {
    case _: FSMLogInfo[A, B] => log.asInstanceOf[FSMLogInfo[A, B]]
    // case _: NDFSMLogInfo[A, B] => log.asInstanceOf[NDFSMLogInfo[A, B]]
  }
  
  // initialize window
  setTitle("Visual Automata")
  setSize(300, 200)
  setLocationRelativeTo(null)
  
  def update() {
    
    // TODO: implement
    
  }
  
  def start() {
    setVisible(true)
  }
}
