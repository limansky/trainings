package webcache

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import akka.http.model.Uri
import scala.util.Try

class Fetcher(workerProps: Props) extends Actor {

  var fetchers = Map.empty[String, ActorRef]

  def receive = {
    case r @ AddRequest(u, _) =>
      getFetcher(u) map (_ ! r)
      
    case Done(h) =>
      fetchers -= h
  }

  def getFetcher(u: String): Option[ActorRef] = {
    Try(Uri(u)).toOption.flatMap { uri =>
      val addr = uri.authority.host.address
      if (addr.nonEmpty) {
        val f = fetchers.getOrElse(addr, context.actorOf(workerProps))
        fetchers += addr -> f
        Some(f)
      } else {
        None
      }
    }
  }
}
