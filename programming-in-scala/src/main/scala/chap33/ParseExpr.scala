package chap33

object ParseExpr extends Arith {

  def main(args: Array[String]): Unit = {
    println("input: " + args(0))
    println(parseAll(expr, args(0)))
  }

}
