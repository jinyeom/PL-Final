package automata

import java.io.{ObjectOutputStream, FileOutputStream, IOException, File}

class LogInfo(logFileName: String) 
  extends Serializable {
  /* Create the file and serialize this object */
  @throws(classOf[IOException])
  @throws(classOf[IllegalStateException])
  def writeToLogFile() = {
    val logFile = new File(logFileName)
    logFile.getParentFile.mkdirs
    
    /* This should not happen if the name generation is correct */
    if (!logFile.createNewFile)
      throw new IllegalStateException("File creation failed due to existing file.")
    
    /* Dump into the file with the specified name */
    try {
      val objOutputStream = new ObjectOutputStream(new FileOutputStream(logFileName))
      objOutputStream writeObject this
      objOutputStream.close()
    } catch {
      /* Maybe this could be handled more gracefully */
      case e: IOException => {
        println("Writing to log " + logFileName + " failed.")
        e.printStackTrace()
      }
    }
  }
}
