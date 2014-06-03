package webcache

import akka.actor.Actor
import spray.routing._
import spray.http._
import scala.concurrent.Future
import scala.slick.lifted.TableQuery
import scala.slick.driver.H2Driver.simple._


class WebCacheRestActor extends Actor with WebCacheRest {

  override def actorRefFactory = context

  override def receive = runRoute(route)
}

trait WebCacheRest extends HttpService {

  val cache = TableQuery[Cache]

  val route =
    path("resources") {
      complete {
//        getResources()
        "ok"
      }
    }

  def getResources() = {// : Future[List[(String, String)]] = {
//    Future {
      val q = for (e <- cache) yield (e.url, e.id)

      q.list
//    }
  }
}
