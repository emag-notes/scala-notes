package chap29.org.stairwaybook.recipe

trait SimpleFoods {
  object Pear extends Food("Pear")
  def allFoods      = List(Apple, Pear)
  def allCategories = Nil
}
