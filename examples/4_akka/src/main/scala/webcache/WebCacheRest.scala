package webcache

import scala.concurrent.ExecutionContextExecutor
import spray.json.DefaultJsonProtocol
import akka.http.model.StatusCodes

case class Resource(id: Int, url: String, cached: Long)

object JsonProtocol extends DefaultJsonProtocol {
  implicit val resourceFormat = jsonFormat3(Resource.apply)
}

trait WebCacheRest {

  import akka.http.server.Directives._
  import akka.http.marshallers.sprayjson.SprayJsonSupport._
  import JsonProtocol._

  implicit val executor: ExecutionContextExecutor

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
    }
}
