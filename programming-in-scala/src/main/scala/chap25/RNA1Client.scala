package chap25

object RNA1Client extends App {

  val xs = List(A, G, T, A)
  RNA1.fromSeq(xs)

  val rna1 = RNA1(A, U, G, G, T)
  println(rna1.length)
  println(rna1.last)
  println(rna1 take 3)
  println(rna1 filter (_ != U))

}
