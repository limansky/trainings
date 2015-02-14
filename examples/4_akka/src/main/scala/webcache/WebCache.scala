package webcache

import akka.actor.ActorSystem
import akka.actor.Props
import akka.io.IO
import akka.http.Http
import akka.stream.ActorFlowMaterializer

object WebCache extends App with WebCacheRest {

  implicit val system = ActorSystem("adserver")
  implicit val materializer = ActorFlowMaterializer()
  override implicit val executor = system.dispatcher

  Http().bind(interface = "0.0.0.0", port = 8080).startHandlingWith(route)
}
