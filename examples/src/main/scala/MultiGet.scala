import dispatch._, Defaults._
import java.io.FileOutputStream
import scala.util.{Success, Failure}

object MultiGet extends App {

  val partsNumber = 5

  def getSinglePart(link: String): Future[Array[Byte]] = {
    Http(url(link) OK as.Bytes)
  }

  def getMultipart(link: String): Future[Array[Byte]] = {

    def getPart(from: Int, to: Option[Int]): Future[Array[Byte]] = {
      val toStr = to.map(_.toString).getOrElse("")
      val u = url(link).addParameter("range", s"bytes=$from-$toStr")
      Http(u OK as.Bytes)
    }

    val f = for {
      resp <- Http(url(link).HEAD)
      h = resp.getHeaders()
      if h.containsKey("Accept-Range") && h.containsKey("Content-Length")
    } yield h.getFirstValue("Content-Length").toInt

    f flatMap { size =>
      val partSize = size / partsNumber
      val tasks = (0 to partsNumber - 1).map(i => getPart(i * partSize, Some((i + 1) * partSize - 1))) :+
                  getPart((partsNumber - 1) * partSize, None)
      Future.sequence(tasks)
    } map {
      _.toArray.flatten
    }
  }

  args.toList match {
    case link :: Nil =>
      getMultipart(link) fallbackTo {
        getSinglePart(link)
      } onComplete {
        case Success(data) =>
          val filename = link.substring(link.lastIndexOf('/') + 1)
          val fs = new FileOutputStream(filename)
          fs.write(data)
          fs.close
          println("Done")
        case Failure(t) =>
          println(s"Failed to fetch file from $link, because of ${t.getMessage}")
      }
    case _ => println("Usage: multiget <link>")
  }
}
