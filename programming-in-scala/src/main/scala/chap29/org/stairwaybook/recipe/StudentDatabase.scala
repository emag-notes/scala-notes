package chap29.org.stairwaybook.recipe

object StudentDatabase extends Database {
  object FrozenFood extends Food("FrozenFood")
  object HeatItUp
      extends Recipe(
        "heat it up",
        List(FrozenFood),
        "Microwave the 'food' for 10 minutes."
      )

  override def allFoods   = List(FrozenFood)
  override def allRecipes = List(HeatItUp)
  override def allCategories = List(
    FoodCategory("edible", List(FrozenFood))
  )
}
