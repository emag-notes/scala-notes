package chap26

object UserTwiceUpper extends App {

  println(userTwiceUpper("DIDI@hotmail.com"))
  println(userTwiceUpper("DIDO@hotmail.com"))
  println(userTwiceUpper("didi@hotmail.com"))

  def userTwiceUpper(s: String) = s match {
    case EMail(Twice(x @ UpperCase()), domain) =>
      s"match: $x in domain  $domain"
    case _ => "no match"
  }

}
