package chap14

import org.scalatest.WordSpec
import org.scalatest.prop.PropertyChecks
import org.scalatest.MustMatchers._
import chap10.Element.elem

class ElementSpecWithScalaCheck extends WordSpec with PropertyChecks {

  "elem result" must {
    "have passed width" in {
      forAll { (w: Int) =>
//        whenever(w > 0) {
        whenever(w > 0 && w < 10) {
          elem('x', w, 3).width must equal(w)
        }
      }
    }
  }

}
