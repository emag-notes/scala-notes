package chap09

import java.io.{ File, PrintWriter }

object WithPrintWriter {

  def withPrintWriter(file: File)(op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  def main(args: Array[String]): Unit = {
    val file = new File("data.txt")

    withPrintWriter(file) { writer =>
      writer.println(new java.util.Date())
    }

  }
}
