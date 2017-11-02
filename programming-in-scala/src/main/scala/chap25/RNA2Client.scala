package chap25

object RNA2Client extends App {

  val rna2 = RNA2(A, U, G, G, T)
  println(rna2.length)
  println(rna2.last)
  println(rna2 take 3)
  println(rna2 filter (_ != U))

  println(rna2 map { case A => T case b => b })
  println(rna2 ++ rna2)

  println(rna2 map Base.toInt)
  println(rna2 ++ List("missing", "data"))
}
