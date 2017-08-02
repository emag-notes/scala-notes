package chap13

package object bobsdelights {

  def showFruit(fruit: Fruit): Unit = {
    import fruit._
    println(s"${name}s are $color")
  }

}
