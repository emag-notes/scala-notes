package chap26

object EMailClient extends App {

  println(EMail.unapply("John@epfl.ch"))
  println(EMail.unapply("John Doe"))

  println(EMail.unapply(EMail.apply("John", "epfl.ch")))
  EMail.unapply("John@epfl.ch") match {
    case Some((u, d)) => println(u + ":" + d)
  }
}
