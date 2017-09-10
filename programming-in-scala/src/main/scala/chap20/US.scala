package chap20

object US extends CurrencyZone {

  abstract class Dollar extends AbstractCurrency {
    def designation: String = "USD"
  }

  type Currency = Dollar

  def make(cents: Long): Dollar = new Dollar {
    val amount = cents
  }

  val Cent         = make(1)
  val Dollar       = make(100)
  val CurrencyUnit = Dollar

}
