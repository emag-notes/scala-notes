package chap26

object DomainClient extends App {

  println(isTomInDotCom("tom@sun.com"))
  println(isTomInDotCom("peter@sun.com"))
  println(isTomInDotCom("tom@acm.org"))

  def isTomInDotCom(s: String): Boolean = s match {
    case EMail("tom", Domain("com", _ *)) => true
    case _                                => false
  }

}
