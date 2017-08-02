package chap13.printmenu

import chap13.bobsdelights.Fruits
import chap13.bobsdelights.showFruit

object PrintMenu extends App {

  for (fruit <- Fruits.menu) {
    showFruit(fruit)
  }

}
