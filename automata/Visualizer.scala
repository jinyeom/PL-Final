import java.io.Source
import java.io.FileFormatException
import java.awt.image.BufferedImage
import java.awt.{ Graphics2D, Color, BasicStroke }
import java.awt.geom._

// Visualizer reads an automata log file and visualizes the automata based on
// the log. The visualization is exported as a PNG image file.
class Visualizer() {

	val NodeSize = 10	// radius of each node
	val EdgeWidth = 2	// edge thickness
	val Margin = 40		// margin of the picture

	// Node depicts a node in a graph.
	class Node(state: String, x: int, y: int) {
			
		private var state = state
		private var x = x
		private var y = y
	
		// position returns the node's x and y coordinate.
		def position(): (int, int) {
			return (x, y)
		}
	
		// draw the node at the argument position x and y.
		def draw() {
	
			// to be implemented
	
		}	
	}

	// Edge depicts an edge in a graph.	
	class Edge(from: Node, to: Node) {
	
		// draw draws an arrow from the input node to the output node.
		def draw() {
	
			// to be implemented
	
		}
	
	}

	private var nodes = collection.mutable.ListBuffer.empty[Node]
	private var edges = collection.mutable.ListBuffer.empty[Edge]
	
	// readLog reads each line of the log file specified with the argument file
	// name and copies the log; throw an expetion if the file is invalid.
	def readLog(filename: String) {
		if (!filename.endsWith(".atm")) {
			throw new FileFormatException("The argument file name must have an 
				extension .atm")
		}	

		// for each line of the log file,
		Source.fromFile(filename).foreach {

			// should read each log line here
			// in some format I don't understand clearly yet
		
			// each time there's a new node in the line, append it to nodes.
			// each time there's a new edge in the line, append it to edges.

			// we'll come back to this.

		}
	}

	// exportDiagram exports a PNG image that depicts the current visualizer's
	// stored nodes and edges.
	def exportDiagram() {
		var nodeList = nodes.toList	
		var edgeList = edges.toList

		// exported image size (width, height)	
		var width = nodeList.length * NodeSize + Margin * 2
		var height = NodeSize + Margin * 2

		var canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
		val g = canvas.createGraphics()

		g.setColor(Color.WHITE)	

		// first draw each node in line.
		nodeList.foreach {
			
		}
	
	}

}
