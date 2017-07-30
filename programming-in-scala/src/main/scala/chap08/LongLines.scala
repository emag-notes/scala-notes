package chap08

import scala.io.Source

object LongLines {

  def processFile(filename: String, width: Int): Unit = {
    val source = Source.fromFile(filename)
    for (line <- source.getLines()) {
      processLine(line)
    }

    def processLine(line: String) = {
      if (line.length > width) {
        println(filename + ": " + line.trim)
      }
    }
  }

}

object FindLongLines extends App {

  val width = args(0).toInt
  for (arg <- args.drop(1)) {
    LongLines.processFile(arg, width)
  }

}