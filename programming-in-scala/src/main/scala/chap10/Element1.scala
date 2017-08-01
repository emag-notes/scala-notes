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

object Element1Client extends App {

  val element = new ArrayElement(Array("foo", "barbar"))
  println(s"element [$element]")
  println(s"element.contents [${element.contents}]")
  println(s"element.height [${element.height}]")
  println(s"element.width  [${element.width}]")
}
