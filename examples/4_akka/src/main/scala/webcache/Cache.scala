package webcache

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.ProvenShape

class Cache(tag: Tag) extends Table[(Int, String, String)](tag, "CACHE") {

  def id: Column[Int] = column[Int]("SUP_ID", O.PrimaryKey)
  def url: Column[String] = column[String]("URL")
  def file: Column[String] = column[String]("FILE")

   def * : ProvenShape[(Int, String, String)] =
    (id, url, file)
}
