package chap20

object CurrencyApp extends App {

  val yenFromDollar = Japan.Yen from US.Dollar * 100
  println(s"yenFromDollar: $yenFromDollar")

  val euroFromYenFromDollar = Europe.Euro from yenFromDollar
  println(s"euroFromYenFromDollar: $euroFromYenFromDollar")

  val dollarFromEuroFromYenFromDollar = US.Dollar from euroFromYenFromDollar
  println(s"dollarFromEuroFromYenFromDollar: $dollarFromEuroFromYenFromDollar")

  val oneHundredPlusDollarFromEuroFromYenFromDollar = US.Dollar * 100 + dollarFromEuroFromYenFromDollar
  println(s"oneHundredPlusDollarFromEuroFromYenFromDollar: $oneHundredPlusDollarFromEuroFromYenFromDollar")

  // compile error
//  US.Dollar + Europe.Euro

}
