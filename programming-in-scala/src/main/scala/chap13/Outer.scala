package chap13

class Outer {

  class Inner {
    private def f() = println("f")

    class InnerMost {
      f() // OK
    }
  }

  // f is not accessible
//  (new Inner).f()
}
