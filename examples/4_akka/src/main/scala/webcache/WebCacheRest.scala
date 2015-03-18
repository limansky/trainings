package webcache

import scala.concurrent.ExecutionContextExecutor
import spray.json.DefaultJsonProtocol
import akka.actor.{ Actor, ActorRef, Props }
import spray.routing.HttpService
import spray.httpx.SprayJsonSupport
import spray.http.StatusCodes

case class AddRequest(url: String, depth: Int)
case class Resource(id: Int, url: String, cached: Long)

object JsonProtocol extends DefaultJsonProtocol {
  implicit val resourceFormat = jsonFormat3(Resource.apply)
  implicit val addFormat = jsonFormat2(AddRequest.apply)
}

class WebCacheRestActor extends Actor with WebCacheRest {

  override val fetcher = context.actorOf(Props(new Fetcher(Props[Worker])))

  override def actorRefFactory = context

  override def receive = runRoute(route)
}

trait WebCacheRest extends HttpService {

  import JsonProtocol._
  import SprayJsonSupport._

  val fetcher: ActorRef

  val route =
    path("resources") {
      complete {
        ResourceManager.getResources
      }
    } ~
      path("resource" / IntNumber) { id =>
        get {
          complete {
            ResourceManager.getById(id)
          }
        } ~
          delete {
            complete {
              if (ResourceManager.delete(id)) StatusCodes.NoContent else StatusCodes.NotFound
            }
          }
      } ~
      path("resource") {
        put {
          entity(as[AddRequest]) { r =>
            complete {
              fetcher ! r
              ResourceManager.add(r.url, "")
            }
          }
        }
      }

}
