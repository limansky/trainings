package webcache

import akka.actor.ActorSystem
import akka.actor.Props
import akka.io.IO
import spray.can.Http

object WebCache extends App {

  implicit val system = ActorSystem("webcache")

  val service = system.actorOf(Props[WebCacheRestActor])

  IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = 8080)
}
