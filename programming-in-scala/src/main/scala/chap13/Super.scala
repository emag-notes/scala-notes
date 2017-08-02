package chap13

package p {

  class Super {
    protected def f() = println("f")
  }

  class Sub extends Super {
    f()
  }

  class Other {
    // f is not accessible
//    (new Super).f()
  }

}
