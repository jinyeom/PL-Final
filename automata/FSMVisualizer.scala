package automata

import javax.swing._
import java.awt.BorderLayout
import java.awt.Container
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Graphics
import java.awt.Color
import scala.collection.mutable.HashMap

// FSMVisualizer given two types for input string and states, and a LogInfo.
class FSMVisualizer[S, A](fsmLog: FSMLogInfo[S, A]) {
  
  /*
   * The user should be able to see the initial state of the automata when the visualizer is first
   * created. When a visualizer is created, read the first state and all transitions from the input
   * string and apply it to the visualizer.
   */
  
  var visitedStates: List[S] = fsmLog.visitedStates()  // list of visited states.
  var currState: S = visitedStates.head                // currState points to the current state.
  var inputString: List[A] = fsmLog.inputString()      // list of transitions in the input string.
  
  // frame for visualization
  var frame: JFrame = new JFrame("FSM Visualizer") 
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.setSize(800, 500)
  
  var label: JLabel = new JLabel("FSM Visualizer", SwingConstants.CENTER)
  frame.getContentPane.add(label, BorderLayout.PAGE_START)              
    
  // graph
  val graph: JFrame = new JFrame("FSM Graph")
  graph.setPreferredSize(new Dimension(350, 350))
  
  // step button
  val step: JButton = new JButton("Step")
  val controls: JPanel = new JPanel()
  controls.add(step)
  frame.add(controls, BorderLayout.PAGE_END)
 
  // input string tabel
  val inputs: JTable = new JTable(10, 2)
  frame.add(inputs, BorderLayout.LINE_START)
  
  frame.pack()
  frame.setVisible(true)
  
  // update is called upon pressing the update button.
  def update() {
    
    // TODO: add button press event
    
  }
}
