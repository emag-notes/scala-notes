package controllers

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(counter: Counter, cc: ControllerComponents)(
    implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      counter.count().map(c => Ok(s"API Limit Remaining: $c")).recover {
        case ex: Exception => ServiceUnavailable(ex.getMessage)
      }
  }

}
