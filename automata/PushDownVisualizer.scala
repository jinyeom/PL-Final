package automata

import javax.swing._
import java.awt.BorderLayout
import java.awt.Container
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.table.DefaultTableModel
import scala.collection.immutable.List
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.HashMap
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.view.mxGraph

class PushDownVisualizer[S, IA, SA](name: String, pdLog: PushDownLogInfo[S, IA, SA]) {
  
  /*
   * The user should be able to see the initial state of the automata when the visualizer is first
   * created. When a visualizer is created, read the first state and all transitions from the input
   * string and apply it to the visualizer.
   */
  
  val visitedStatesAndStacks: List[(S, List[SA])] = pdLog.visitedStatesAndStacks()
  
  var currState: (S, List[SA]) = visitedStatesAndStacks.head       
  var nextStates: List[(S, List[SA])] = visitedStatesAndStacks.tail   
  var inputString: List[IA] = pdLog.inputString()
  
  val nodeSize: Int = 40
  val gapSize: Int = 20
  val graphWidth: Int = 500
  val graphHeight: Int = 500
  
  // position of the next node to be drawn
  var nextRow: Int = 20
  var nextCol: Int = 20
  
  // nodes and edges that compose 
  var nodes: HashMap[S, AnyRef] = HashMap.empty 
  var edges: ListBuffer[IA] = ListBuffer.empty
  
  // frame for visualization
  val frame: JFrame = new JFrame("Push Down Visualizer") 
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  
  // label (name) of the automaton
  val label: JLabel = new JLabel(name, SwingConstants.CENTER)
  frame.getContentPane.add(label, BorderLayout.PAGE_START)   
    
  // input string table
  val inputs: JTable = new JTable(inputString.size + 1, 1)
  inputs.setValueAt("Inputs", 0, 0)
  
  var i: Int = 1
  for (input <- inputString) {
    inputs.setValueAt(input.toString(), i, 0)
    i += 1
  }
  frame.add(inputs, BorderLayout.LINE_START)
  
  // graph to visualize the automaton
  val graph: JFrame = new JFrame("Push Down Graph")
  graph.setPreferredSize(new Dimension(graphWidth, graphHeight))
  
  val g: mxGraph = new mxGraph()
  val parent: AnyRef = g.getDefaultParent
  
  g.getModel().beginUpdate()
  g.setAllowDanglingEdges(false)
  g.setCellsResizable(false)

  // add a node for each state in the FSM.
  for (state <- pdLog.states()) {
    var node: AnyRef = g.insertVertex(parent, null, state.toString(), nextCol, nextRow, 
        nodeSize, nodeSize, "shape=ellipse;perimeter=ellipsePerimeter")
        
    nextCol += (nodeSize + gapSize)
    if (nextCol >= graphWidth) {
      nextCol = 20
      nextRow += (nodeSize + gapSize)
    }
    
    nodes += (state -> node)
  }
  
  for (((from, stack), paths) <- pdLog.transitions()) {
    for ((edge, (to, stackOp)) <- paths) {
      g.insertEdge(parent, null, "", nodes(from), nodes(to))
    }
  }
  
  frame.setSize(500, 500)
  
  g.getModel().endUpdate()
  
  val graphComponent: mxGraphComponent = new mxGraphComponent(g)
  frame.getContentPane.add(graphComponent)
  
  // stack area
  val stack: JTextArea = new JTextArea(2, 10)
  stack.setEditable(false)
  frame.add(stack, BorderLayout.LINE_END)
  
  // StepListener is an object that implements ActionListener 
  object StepListener extends ActionListener {
    def actionPerformed(e: ActionEvent) {
      if (nextStates.length != 0) {
        currState = nextStates.head
        nextStates = nextStates.tail
        
        // remove the current input
        inputs.getModel().asInstanceOf[DefaultTableModel].removeRow(1)
        
        // for debugging
        println(currState)
        println(nextStates)
      }
    }
  }
  
  // step button
  val step: JButton = new JButton("Step")
  step.addActionListener(StepListener)
  
  val controls: JPanel = new JPanel()
  controls.add(step)
  frame.add(controls, BorderLayout.PAGE_END)

  frame.setVisible(true)
}
