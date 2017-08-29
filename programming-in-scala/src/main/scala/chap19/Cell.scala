package chap19

class Cell[T](init: T) {

  private[this] var current = init
  def get: T                = current
  def set(x: T)             = { current = x }

}
