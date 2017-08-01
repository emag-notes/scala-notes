package chap10

import chap10.Element.elem

abstract class Element {

  def contents: Array[String]

  /*
   * フィールドとしての表現。どちらにしてもクライアントのコードは一緒
   * フィールドアクセスの方がメソッド呼び出しよりわずかに速い
   * メモリはフィールドのほうが使う
   */
//  val height = contents.length
  def height: Int = contents.length

  def width: Int =
    if (height == 0) 0
    else contents(0).length

  def above(that: Element): Element =
    elem(this.contents ++ that.contents)

  def beside(that: Element): Element = {
    elem(for ((line1, line2) <- this.contents zip that.contents) yield line1 + line2)
  }

  override def toString: String = contents mkString "\n"

}

object Element {

  private class ArrayElement(
      val contents: Array[String]
  ) extends Element

  private class LineElement(s: String) extends Element {
    val contents             = Array(s)
    override def height: Int = 1
    override def width: Int  = s.length
  }

  private class UniformElement(
      ch: Char,
      override val width: Int,
      override val height: Int
  ) extends Element {
    private val line = ch.toString * width
    def contents     = Array.fill(height)(line)
  }

  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)

  def elem(line: String): Element =
    new LineElement(line)

  def elem(char: Char, width: Int, height: Int): Element =
    new UniformElement(char, width, height)

}

object ElementClient extends App {

  val element1: Element = elem(Array("foo", "barbar"))
  println("element1:")
  println(element1)
  println(s"element1.contents [${element1.contents}]")
  println(s"element1.height [${element1.height}]")
  println(s"element1.width  [${element1.width}]")

  println("------")

  val element2: Element = elem("foo")
  println("element2:")
  println(element2)
  println(s"element2.contents [${element2.contents}]")
  println(s"element2.height [${element2.height}]")
  println(s"element2.width  [${element2.width}]")

  println("------")

  val element3: Element = elem('x', 2, 3)
  println("element3:")
  println(element3)
  println(s"element3.contents [${element3.contents}]")
  println(s"element3.height [${element3.height}]")
  println(s"element3.width  [${element3.width}]")
}
