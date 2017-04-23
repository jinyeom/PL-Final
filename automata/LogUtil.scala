package automata

import java.io.File

object LogUtil {
  
  val FILE_EXTENSION = ".atm"
  val DIRECTORY = "src/logs/"
  val DEFAULT_NAME = null
	
  /**
   * Generates a file name that doesn't exist
   */
	def generateFileName(start: String) = {
	  var fileName = if (start != DEFAULT_NAME) start else getClass.getSimpleName
	  val predicate = (i: Int) => new File(DIRECTORY + fileName + i.toString + FILE_EXTENSION) exists
	  val num = scala.collection.immutable.Stream.from(0).dropWhile(predicate).head
	  fileName + num.toString
	}
  
  /**
   * Returns the full name of the log file from the
   * base name
   */
  def fullFileName(fileName: String) = DIRECTORY + fileName + FILE_EXTENSION
	
	/**
	 * Returns the file name that should be used for the log file
	 */
  def makeFileName(fileName: String) = {
    if (fileName == DEFAULT_NAME ||
      fileName != DEFAULT_NAME && (new File(fullFileName(fileName)).exists)) {
      
      /* Make a new file name that is unique */
      generateFileName(fileName)
    } else {
      /* What was passed in is fine */
      fileName
    }
	}
  
  
	
}