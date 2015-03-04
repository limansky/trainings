package webcache

import akka.actor.ActorSystem
import akka.actor.Props
import akka.io.IO
import akka.http.Http
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.Sink

object WebCache extends App with WebCacheRest {

  implicit val system = ActorSystem("adserver")
  override implicit val materializer = ActorFlowMaterializer()
  override implicit val executor = system.dispatcher

  val fetcher = system.actorOf(Props(new Fetcher(Props[Worker])))

  //FIXME: Workaround https://github.com/akka/akka/issues/16972 is fixed
  Http().bind(interface = "0.0.0.0", port = 8080).to(Sink.foreach {
    conn => conn.flow.join(route).run()
  }).run
}
