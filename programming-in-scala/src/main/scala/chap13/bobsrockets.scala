package chap13

package bobsrockets {
  package navigation {

    private[bobsrockets] class Navigator {

      protected[navigation] def useStartChart() = {}

      class LegOfJourney {
        private[Navigator] val distance = 100
      }

      private[this] var speed = 200
      private val speed2      = speed * 2 // accessible

      (new LegOfJourney).distance // accessible

      class NavigatorClient {
        val n = new Navigator
//        val s = n.speed // not accessible
        val s2 = n.speed2
      }

    }

  }

  package launch {
    import navigation._

    object Vehicle {
      private[launch] val guide = new Navigator
    }

  }
}
