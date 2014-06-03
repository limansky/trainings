package webcache

import akka.actor.ActorSystem
import akka.actor.Props
import akka.io.IO
import spray.can.Http

object WebCache extends App {

  implicit val system = ActorSystem("adserver")

  // create and start our service actor
  val service = system.actorOf(Props[WebCacheRestActor], "adserver-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = 8080)


}