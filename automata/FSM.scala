package automata

import java.io._

class FSM[S, A](transitions: List[(S,List[(A,S)])], acceptStates: List[S]) {
	
		private val transitionMap = scala.collection.mutable.HashMap.empty[S, List[(A,S)]]
		private var shouldLogState = false
		private var logFileOpen = false
		private var logFileName: String = generateFileName(DEFAULT_NAME)

		transitions map (p => 
			transitionMap += (p._1 -> p._2)
		)

		def accept(initState: S, seq: List[A]) : Boolean = {
			if (seq.length > 0){
				transition (initState, seq.head) match {
					case Some(nextState) => accept(nextState, seq.tail)
					case None => false
				}
			} else {
				acceptStates contains initState
			}
		}

		def transition(s : S, a : A) = {
			transitionMap get s match {
				case Some(transitionList) => {
					val transitions = transitionList filter (_._1 == a)
					if ((transitions length) == 0) {
						None
					} else {
						Some(transitions.head _2)
					}
				}
				case None => None
			}
		}
		
		/**
		 * Logging functions
		 */
		
		/**
		 * Generates a file name that doesn't exist
		 */
		private def generateFileName(start: String) = {
			var fileName = if (start != DEFAULT_NAME) start else getClass.toString()
			val predicate = (i: Int) => new File(fileName + i.toString) exists
			val num = scala.collection.immutable.Stream.from(0).dropWhile(predicate).head
			fileName + num.toString
		}
		
		/**
		 * Set the name of the file to log to fileName
		 * Sets shouldLogState to true
		 */
		@throws(classOf[IllegalStateException])
		def setLogFileName(fileName: String) = {
			/* Shouldn't even be logging */
			if (!shouldLogState)
				throw new IllegalStateException("Field shouldLogState must be true to set file name")
			
			logFileName = 
				if (fileName == DEFAULT_NAME ||
						fileName != DEFAULT_NAME && (new File(fileName)).exists) {
					generateFileName(fileName) + FILE_EXTENSION
				} else {
					fileName + FILE_EXTENSION
				}
			setShouldLogState(true)
		}
		
		/**
		 * Set whether this automaton should log state
		 * Also this code should probably be reused
		 * Since it's gonna be annoying to rewrite
		 * TODO generalize when done
		 */
		def setShouldLogState(b: Boolean) = {
			/*
			 * If shouldLogState
			 * 		if !b
			 * 			delete the file (?)
			 * 		else
			 * 			do nothing
			 * else
			 * 		if b
			 * 			open the file and write the header out to it
			 * 			also, make sure that when accept finishes we update the file name
			 * 			consecutive runs should not write to the same file
			 * 			i admit it's a weird design decision but hey it'll work
			 * 			too tired to write this correctly so this is all that's going to be here for now
			 */
			shouldLogState = b
		}
		
		/**
		 * Get value of shouldLogState
		 */
		def willLog = shouldLogState
		
		/**
		 * Get name of file that the log will
		 * be written to
		 */
		def loggingToFile = logFileName
		
		/**
		 * Dump the transition map
		 */
		private def logHeader() = {
			val foldFun = (acc: String, next: Any) =>  acc + next.toString
			
			"transitions:" + transitionMap.foldLeft("")(foldFun) + "\n" +
			"accept:" + acceptStates + "\n"
		}
		
		/**
		 * Dump the current state 
		 */
		private def logInfo(currentState: S) = {
			"state: " + currentState.toString + "\n"
		}

	}