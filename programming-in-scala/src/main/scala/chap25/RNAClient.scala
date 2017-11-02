package chap25

object RNAClient extends App {

  val rna = RNA(A, U, G, G, T)
  println(rna.length)
  println(rna.last)
  println(rna take 3)
  println(rna filter (_ != U))

  println(rna map { case A => T case b => b })
  println(rna ++ rna)

  println(rna map Base.toInt)
  println(rna ++ List("missing", "data"))
}
