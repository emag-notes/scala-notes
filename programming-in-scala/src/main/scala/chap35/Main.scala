package chap35

import swing._

object Main extends SimpleSwingApplication {

  override def top = new MainFrame {
    title = "ScalaSheet"
    contents = new Spreadsheet(100, 26)
  }

}
