package chap04

import chap04.ChecksumAccumulator.calculate

object Summer {

  def main(args: Array[String]): Unit = {
    for (arg <- args) {
      println(arg + ": " + calculate(arg))
    }
  }

}
