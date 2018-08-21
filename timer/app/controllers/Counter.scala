package controllers

import java.time.LocalDateTime
import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

trait Counter {
  def count(): Future[Int]
}

@Singleton
class DefaultCounter @Inject()(system: ActorSystem)(
    implicit ec: ExecutionContext)
    extends Counter {

  private val Limit: Int = 3
  private val apiRestrictionTime = 5.seconds

  private val apiLimitRemaining: AtomicInteger = new AtomicInteger(Limit)

  private val apiRestrictedDueToRateLimitExceeded: AtomicBoolean = new AtomicBoolean(false)

  override def count(): Future[Int] = {
    if (isRestrictedToUseTheApi)
      Future.failed(new RuntimeException("sorry service unavailable"))
    else {
      requestCounterApi().recover {
        case ex: Exception =>
          log("close")
          restrictApi()
          scheduleLiftingApiRestriction()
          throw ex
      }
    }
  }

  private def isRestrictedToUseTheApi: Boolean = apiRestrictedDueToRateLimitExceeded.get() // || or other reason...

  private def requestCounterApi(): Future[Int] =
    apiLimitRemaining.decrementAndGet() match {
      case c if c >= 0 => Future(c)
      case _ =>
        Future.failed(new RuntimeException("Counter API limit exceeded"))
    }

  private def scheduleLiftingApiRestriction(): Unit = {
    system.scheduler.scheduleOnce(apiRestrictionTime) {
      log("open")
      liftApiRestriction()
      apiLimitRemaining.set(Limit)
    }
  }

  private def restrictApi(): Unit = {
    apiRestrictedDueToRateLimitExceeded.compareAndSet(false, true)
  }

  private def liftApiRestriction(): Unit = {
    apiRestrictedDueToRateLimitExceeded.compareAndSet(true, false)
  }

  private def log(message: String): Unit = {
    println(s"### ${LocalDateTime.now()}: $message")
  }

}
