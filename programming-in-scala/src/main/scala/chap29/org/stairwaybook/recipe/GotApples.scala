package chap29.org.stairwaybook.recipe

object GotApples extends App {
  val db: Database =
    if (args(0) == "student") StudentDatabase
    else SimpleDatabase

  object browser extends Browser {
    val database: db.type = db
  }

  val apple = SimpleDatabase.foodNamed("Apple").get
  browser.recipesUsing(apple).foreach(println)
}
