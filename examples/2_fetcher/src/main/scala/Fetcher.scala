import scala.io.Source
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{ Success, Failure }

object Fetcher extends App {
  def getPage(url: String): Future[String] = {
    Future {
      Source.fromURL(url).mkString
    }
  }

  val tasks = Future.sequence(args.toList.map { url =>
    val f = getPage(url)

    f onComplete {
      case Success(p) => println(url + ": " + p.take(200))
      case Failure(e) => println(s"Failed to get content from $url, ${e.getMessage}")
    }

    f
  })

  Await.ready(tasks, Duration.Inf)
}
