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
      getWorker(u) map (_ ! r)

    case Done(h) =>
      workers -= h
  }

  def getWorker(u: String): Option[ActorRef] = {
    Try(Uri(u)).toOption.flatMap { uri =>
      val addr = uri.withPath(Uri.Path.Empty).toString()
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
