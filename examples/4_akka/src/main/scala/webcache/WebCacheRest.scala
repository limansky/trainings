package webcache

import scala.concurrent.ExecutionContextExecutor
import spray.json.DefaultJsonProtocol
import akka.http.model.StatusCodes
import akka.stream.FlowMaterializer

case class AddRequest(url: String, depth: Int)
case class Resource(id: Int, url: String, cached: Long)

object JsonProtocol extends DefaultJsonProtocol {
  implicit val resourceFormat = jsonFormat3(Resource.apply)
  implicit val addFormat = jsonFormat2(AddRequest.apply)
}

trait WebCacheRest {

  import akka.http.server.Directives._
  import akka.http.marshallers.sprayjson.SprayJsonSupport._
  import JsonProtocol._

  implicit val executor: ExecutionContextExecutor
  implicit val materializer: FlowMaterializer

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
              ResourceManager.add(r.url, "")
            }
          }
        }
      }

}
