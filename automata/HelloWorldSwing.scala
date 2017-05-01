package start

import javax.swing._
import java.awt.BorderLayout
import java.awt.Container
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.Graphics
import java.awt.Color

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
    
    val graph: JButton = new JButton("FSM Graph")
    graph.setPreferredSize(new Dimension(350, 350))
    
//    val canvas = new BufferedImage(size._1, 
//    
//    val g = new Graphics() 
//    g.setColor(Color.RED)
//    g.drawOval(0, 50, 30, 30)
//    
//    graph.add(g)
    
    frame.add(graph, BorderLayout.CENTER)
    
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
