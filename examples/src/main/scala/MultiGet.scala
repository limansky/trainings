import dispatch._, Defaults._

object MultiGet extends App {

  val parts = 5

  def getSinglePart(link: String): Future[Array[Byte]] = {
    Http(url(link) OK as.Bytes)
  }

  def getMultipart(link: String) = {
    val f = for {
      resp <- Http(url(link).HEAD)
      val h = resp.getHeaders()
      if h.containsKey("Accept-Range") && h.containsKey("Content-Length")
    } yield h.getFirstValue("Content-Length").toInt

    f recoverWith { case e  => getSinglePart(link) }

    f onFailure {
      case t => println(s"Failed to fetch file from $link, because of ${t.getMessage}")
    }
  }

  args.headOption.foreach(getMultipart)
}