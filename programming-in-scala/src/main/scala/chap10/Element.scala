package chap10

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
}

class ArrayElement(
    val contents: Array[String]
) extends Element

class LineElement(s: String) extends Element {
  val contents             = Array(s)
  override def height: Int = 1
  override def width: Int  = s.length
}

class UniformElement(
    ch: Char,
    override val width: Int,
    override val height: Int
) extends Element {
  private val line = ch.toString * width
  def contents     = Array.fill(height)(line)
}

object ElementClient extends App {

  val element1: Element = new ArrayElement(Array("foo", "barbar"))
  println(s"element1 [$element1]")
  println(s"element1.contents [${element1.contents}]")
  println(s"element1.height [${element1.height}]")
  println(s"element1.width  [${element1.width}]")

  println("------")

  val element2: Element = new LineElement("foo")
  println(s"element2 [$element2]")
  println(s"element2.contents [${element2.contents}]")
  println(s"element2.height [${element2.height}]")
  println(s"element2.width  [${element2.width}]")

  println("------")

  val element3: Element = new UniformElement('x', 2, 3)
  println(s"element3 [$element3]")
  println(s"element3.contents [${element3.contents}]")
  println(s"element3.height [${element3.height}]")
  println(s"element3.width  [${element3.width}]")
}
