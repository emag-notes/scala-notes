package chap04

import chap04.ChecksumAccumulator.calculate

object FallWinterSpringSummer extends App {

  for (season <- List("fall", "winter", "spring")) {
    println(season + ": " + calculate(season))
  }

}
