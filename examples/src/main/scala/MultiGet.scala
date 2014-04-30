import dispatch._, Defaults._
import java.io.FileOutputStream
import scala.util.{Success, Failure}

object MultiGet extends App {

  val parts = 5

  def getSinglePart(link: String): Future[Array[Byte]] = {
    Http(url(link) OK as.Bytes)
  }

  def getMultipart(link: String) = {

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
      val partSize = size / parts
      val tasks = (0 to parts - 1).map(i => getPart(i * partSize, Some((i + 1) * partSize - 1))) :+
                  getPart((parts - 1) * partSize, None)
      Future.sequence(tasks)
    } fallbackTo {
      getSinglePart(link)
    }

    f onComplete {
      case Success(data) =>
        val filename = link.substring(link.lastIndexOf('/') + 1)
        val fs = new FileOutputStream(filename)
        fs.write(data)
        fs.close
        println("Done")
      case Failure(t) =>
        println(s"Failed to fetch file from $link, because of ${t.getMessage}")
    }
  }

  args.headOption.foreach(getMultipart)
}
