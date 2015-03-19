package webcache

import akka.actor.Actor
import scala.collection.immutable.Queue
import spray.http.Uri
import scala.concurrent.Future

case class Done(host: String)

class Worker(base: String) extends Actor {
  import context.dispatcher
  import spray.client.pipelining._

  var queue = Queue.empty[AddRequest]

  val pipeline = sendReceive ~> unmarshal[String]
  val baseUri = Uri(base)

  def receive = {
    case r: AddRequest =>
      queue.enqueue(r)
      fetchPage(r)
  }

  def fetchPage(r: AddRequest): Unit = {
    val uri = Uri(r.url).resolvedAgainst(baseUri)
    val content = pipeline(Get(uri))

    if (r.depth > 0) {
      content onSuccess {
        case c =>
          val reqs = getLinks(c) map (l => AddRequest(l, r.depth - 1))
          reqs foreach queue.enqueue
      }

    }
  }

  def getLinks(c: String): List[String] = {
    val r = """<a\s[^h]*href\s*=\s*("[^"]+"|'[^']+'|[^ ]+)[^>]*>""".r
    (for {
      r(u) <- r findAllIn c

    } yield u).toList
  }
}
