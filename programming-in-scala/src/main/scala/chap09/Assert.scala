package chap09

object Assert {

  val assertionEnabled = true

  def myAssert(predicate: () => Boolean) =
    if (assertionEnabled && !predicate())
      throw new AssertionError

  def byNameAssert(predicate: => Boolean) =
    if (assertionEnabled && !predicate)
      throw new AssertionError

  def boolAssert(predicate: Boolean) =
    if (assertionEnabled && !predicate)
      throw new AssertionError

  def main(args: Array[String]): Unit = {
    myAssert(() => 5 > 3)
    try {
      myAssert(() => 5 < 3)
    } catch {
      case ex: AssertionError => println(s"ex [$ex]")
    }

    /*
     * 5 > 3 は byNameAssert 呼び出し前に評価されないため、
     * assertionEnabled が false の時には実行されない
     */
    byNameAssert(5 > 3)
    try {
      byNameAssert(5 < 3)
    } catch {
      case ex: AssertionError => println(s"ex [$ex]")
    }

    /*
     * 5 > 3 は boolAssert 呼び出し前に評価されるため、
     * assertionEnabled が false の時にも実行される(=副作用あり)
     */
    boolAssert(5 > 3)
    try {
      boolAssert(5 < 3)
    } catch {
      case ex: AssertionError => println(s"ex [$ex]")
    }
  }
}
