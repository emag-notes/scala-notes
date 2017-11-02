package chap28

object CCThermClient extends App {

  val therm = new CCTherm {
    override val description   = "hot dog #5"
    override val yearMade      = 1952
    override val dateObtained  = "March 14, 2006"
    override val bookPrice     = 2199
    override val purchasePrice = 500
    override val condition     = 9
  }
  val node = therm.toXML
  println(node)

  println(therm.fromXML(node))

  xml.XML.save("therm1.xml", node)

  val loadNode = xml.XML.loadFile("therm1.xml")
  println(therm.fromXML(loadNode))
}
