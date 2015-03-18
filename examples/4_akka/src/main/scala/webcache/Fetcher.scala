package webcache

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import scala.util.Try
import spray.http.Uri

class Fetcher(workerProps: Props) extends Actor {

  var workers = Map.empty[String, ActorRef]

  def receive = {
    case r @ AddRequest(u, _) =>
      getFetcher(u) map (_ ! r)

    case Done(h) =>
      workers -= h
  }

  def getFetcher(u: String): Option[ActorRef] = {
    Try(Uri(u)).toOption.flatMap { uri =>
      val addr = uri.authority.host.address
      if (addr.nonEmpty) {
        val f = workers.getOrElse(addr, context.actorOf(workerProps))
        workers += addr -> f
        Some(f)
      } else {
        None
      }
    }
  }
}
