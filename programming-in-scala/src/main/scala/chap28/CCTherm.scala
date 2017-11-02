package chap28

abstract class CCTherm {
  val description: String
  val yearMade: Int
  val dateObtained: String
  val bookPrice: Int // 単位: 米国セント
  val purchasePrice: Int // 単位: 米国セント
  val condition: Int // 1 - 10
  override def toString: String = description

  def toXML =
    <cctherm>
      <description>{description}</description>
      <yearMade>{yearMade}</yearMade>
      <dateObrtained>{dateObtained}</dateObrtained>
      <bookPrice>{bookPrice}</bookPrice>
      <purchasePrice>{purchasePrice}</purchasePrice>
      <condition>{condition}</condition>
    </cctherm>

  def fromXML(node: scala.xml.Node): CCTherm =
    new CCTherm {
      override val description: String = (node \ "description").text
      override val yearMade: Int = (node \ "yearMade").text.toInt
      override val dateObtained: String = (node \ "dateObtained").text
      override val bookPrice: Int = (node \ "bookPrice").text.toInt
      override val purchasePrice: Int = (node \ "purchasePrice").text.toInt
      override val condition: Int = (node \ "condition").text.toInt
    }
}
