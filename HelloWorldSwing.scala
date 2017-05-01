package start

import javax.swing._
import java.awt.BorderLayout
import java.awt.Container
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Graphics
import java.awt.Color
import java.awt.image.BufferedImage
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.view.mxGraph


object HelloWorldSwing {
  
  private def createAndShowGUI() {
    val frame: JFrame = new JFrame("HelloWorldSwing")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    
    val label: JLabel = new JLabel("Hello World")
    frame.getContentPane.add(label)
//    frame.setSize(800, 500)
    
    // 'packs' components
    //    frame.pack()
    frame.setVisible(true)
  }
  
  private def PDFSMGUI() {
    val frame: JFrame = new JFrame("FSM")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    
    val label: JLabel = new JLabel("Push Down Finite State Machine", SwingConstants.CENTER)
    frame.getContentPane.add(label, BorderLayout.PAGE_START)
    
    val graph: JFrame = new JFrame()
    graph.setPreferredSize(new Dimension(350, 350))
    
    val g: mxGraph = new mxGraph()
    val parent: AnyRef = g.getDefaultParent
    
    g.getModel().beginUpdate()
    g.setAllowDanglingEdges(false)
    try {
      val v1: AnyRef = g.insertVertex(parent, null, "A", 20, 20, 40, 40, "shape=ellipse;perimeter=ellipsePerimeter")
      val v2: AnyRef = g.insertVertex(parent, null, "B", 200, 150, 40, 40, "shape=ellipse;perimeter=ellipsePerimeter")
      val v3: AnyRef = g.insertVertex(parent, null, "C", 200, 0, 40, 40, "shape=ellipse;perimeter=ellipsePerimeter")
      g.insertEdge(parent, null, "", v1, v3)
      g.insertEdge(parent, null, "", v2, v3)
      g.insertEdge(parent, null, "", v1, v2)
    } finally {
      g.getModel().endUpdate()
    }
    
    
    
    val graphComponent: mxGraphComponent = new mxGraphComponent(g)
    
    frame.getContentPane.add(graphComponent)
    
    val stack: JTextArea = new JTextArea(2, 10)
    stack.setEditable(false)
    frame.add(stack, BorderLayout.LINE_END)
    
    val step: JButton = new JButton("Step")
//    frame.add(b2, BorderLayout.LINE_START)
    
    val controls: JPanel = new JPanel()
    controls.add(step)
    frame.add(controls, BorderLayout.PAGE_END)
   
    val inputs: JTable = new JTable(10, 2)
    frame.add(inputs, BorderLayout.LINE_START)
    
//    frame.setSize(800, 500)
    frame.pack()
    
    frame.setVisible(true)
  }
  
  private def FSMGUI() {
    val frame: JFrame = new JFrame("FSM")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    
    val label: JLabel = new JLabel("Finite State Machine", SwingConstants.CENTER)
    frame.getContentPane.add(label, BorderLayout.PAGE_START)
    
    val graph: JButton = new JButton("FSM Graph")
    graph.setPreferredSize(new Dimension(350, 350))
    frame.add(graph, BorderLayout.CENTER)
    
    val step: JButton = new JButton("Step")
//    frame.add(b2, BorderLayout.LINE_START)
    
    val controls: JPanel = new JPanel()
    controls.add(step)
    frame.add(controls, BorderLayout.PAGE_END)
   
    val inputs: JTable = new JTable(10, 2)
    frame.add(inputs, BorderLayout.LINE_START)
    
//    frame.setSize(800, 500)
    frame.pack()
    
    frame.setVisible(true)
  }
  
  def main(args: Array[String]) {
    if (true) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        def run() {
          PDFSMGUI()
        }
      })
    } else {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        def run() {
          FSMGUI()
        }
      })
    }
  }
}