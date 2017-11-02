package chap26

object ExpandedEMailClient extends App {

  val s = "tom@support.epfl.ch"

  val ExpandedEMail(name, topdom, subdoms @ _ *) = s
  println(s"$name, $topdom, $subdoms")

}
