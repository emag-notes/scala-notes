package chap34

import scala.swing._

object SecondSwingApp extends SimpleSwingApplication {

  override def top = new MainFrame {
    title = "Second Swing App"

    val button = new Button {
      text = "Click me"
    }

    val label = new Label {
      text = "No button clicks registered"
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += button
      contents += label
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }
  }

}
