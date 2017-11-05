package chap34

import scala.swing._

object FirstSwingApp extends SimpleSwingApplication {

  override def top = new MainFrame {
    title = "First Swing App"
    contents = new Button {
      text = "Click me"
    }
  }

}
