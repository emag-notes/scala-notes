package chap10

abstract class Element1 {

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
) extends Element1

class LineElement(s: String) extends ArrayElement(Array(s)) {
  override def height: Int = 1
  override def width: Int  = s.length
}

object Element1Client extends App {

  val element1: Element1 = new ArrayElement(Array("foo", "barbar"))
  println(s"element1 [$element1]")
  println(s"element1.contents [${element1.contents}]")
  println(s"element1.height [${element1.height}]")
  println(s"element1.width  [${element1.width}]")

  println("------")

  val element2: Element1 = new LineElement("foo")
  println(s"element2 [$element2]")
  println(s"element2.contents [${element2.contents}]")
  println(s"element2.height [${element2.height}]")
  println(s"element2.width  [${element2.width}]")
}
