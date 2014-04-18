import scala.io.Source
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object Fetcher extends App {
  def getPage(url: String): Future[String] = {
    future {
      Source.fromURL(url).mkString
    }
  }

  args.foreach { url =>
    val f = getPage(url)

    f onComplete {
      case Success(p) => println(url + ": " + p.take(200))
      case Failure(_) => println("Failed to get content from " + url)
    }
  }
}
