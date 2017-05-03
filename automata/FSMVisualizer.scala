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
import com.mxgraph.model.mxCell

// FSMVisualizer given two types for input string and states, and a LogInfo.
class FSMVisualizer[S, A](name: String, fsmLog: FSMLogInfo[S, A]) {
  
  /*
   * The user should be able to see the initial state of the automata when the visualizer is first
   * created. When a visualizer is created, read the first state and all transitions from the input
   * string and apply it to the visualizer.
   */
  
  val visitedStates: List[S] = fsmLog.visitedStates()  // list of visited states.
  
  var currState: S = visitedStates.head       
  var nextStates: List[S] = visitedStates.tail   
  var inputString: List[A] = fsmLog.inputString()
  
  val nodeSize: Int = 40
  val gapSize: Int = 40
  val graphWidth: Int = 500
  val graphHeight: Int = 500
  
  // position of the next node to be drawn
  var nextRow: Int = 20
  var nextCol: Int = 20
  
  // nodes and edges that compose 
  var nodes: HashMap[S, AnyRef] = HashMap.empty 
  var edges: ListBuffer[A] = ListBuffer.empty
  
  // frame for visualization
  val frame: JFrame = new JFrame("FSM Visualizer") 
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.setSize(500, 500)
  
  // label (name) of the automaton
  val label: JLabel = new JLabel(name, SwingConstants.CENTER)
  frame.getContentPane.add(label, BorderLayout.PAGE_START)   
    
  // input string table
  val inputs: JTable = new JTable(inputString.size, 1)
//  inputs.setValueAt("Inputs", 0, 0)
  
  var i: Int = 0
  for (input <- inputString) {
    inputs.setValueAt(input.toString(), i, 0)
    i += 1
  }
  frame.add(inputs, BorderLayout.LINE_START)
  
  // graph to visualize the automaton
  val graph: JFrame = new JFrame("FSM Graph")
  graph.setPreferredSize(new Dimension(graphWidth, graphHeight))
  
  val g: mxGraph = new mxGraph()
  val parent: AnyRef = g.getDefaultParent
  
  g.getModel().beginUpdate()
  g.setAllowDanglingEdges(false)
  g.setCellsResizable(false)

  // add a node for each state in the FSM.
  for (state <- fsmLog.states()) {
    var node: AnyRef = g.insertVertex(parent, null, state.toString(), nextCol, nextRow, 
        nodeSize, nodeSize, "shape=ellipse;perimeter=ellipsePerimeter;fillColor=#eeeeee")
        
    nextCol += (nodeSize + gapSize)
    if (nextCol >= graphWidth) {
      nextCol = 20
      nextRow += (nodeSize + gapSize)
    }
    
    nodes += (state -> node)
  }
  
  // change the currState's color to active.
  nodes(currState).asInstanceOf[mxCell].setStyle(
      "shape=ellipse;perimeter=ellipsePerimeter;fillColor=#ef5350")
  
  // add an edge for each transition
  for ((from, paths) <- fsmLog.transitions()) {
    for ((edge, to) <- paths) {
      g.insertEdge(parent, null, edge.toString(), nodes(from), nodes(to))
    }
  }
  g.getModel().endUpdate()
  
  val graphComponent: mxGraphComponent = new mxGraphComponent(g)
  frame.getContentPane.add(graphComponent)
  
  // StepListener is an object that implements ActionListener 
  object StepListener extends ActionListener {
    def actionPerformed(e: ActionEvent) {
      if (nextStates.length != 0) {
        g.getModel().beginUpdate()
        
        // change the currState's color to inactive color
        nodes(currState).asInstanceOf[mxCell].setStyle(
          "shape=ellipse;perimeter=ellipsePerimeter;fillColor=#eeeeee")
        
        currState = nextStates.head
        nextStates = nextStates.tail
        
        // change the new currState's color to active color
        nodes(currState).asInstanceOf[mxCell].setStyle(
          "shape=ellipse;perimeter=ellipsePerimeter;fillColor=#ef5350")
        
        // remove the current input
        inputs.getModel().asInstanceOf[DefaultTableModel].removeRow(0)
        
        g.getModel().endUpdate()
        g.refresh()
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
