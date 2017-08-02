package chap14

import chap10.Element.elem
import org.scalatest.FunSuite

class ElementSuite extends FunSuite {

  test("elem result should have passed width") {
    val ele1 = elem('x', 2, 3)
    assert(ele1.width == 2)

    assertResult(2) {
      elem('x', 2, 3).width
    }

//    assertThrows[IllegalArgumentException] {
//      elem('x', -2, 3)
//    }

    val caught = intercept[ArithmeticException] {
      1 / 0
    }
    assert(caught.getMessage == "/ by zero")
  }

}
