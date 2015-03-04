package webcache

import akka.actor.Actor

case class Done(host: String)

class Worker(host: String) extends Actor {

  def receive = {
    case AddRequest(u, d) =>
      sender ! Done(host)
  }
}
