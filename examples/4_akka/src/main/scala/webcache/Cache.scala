package webcache

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.ProvenShape
import java.sql.Timestamp

class Cache(tag: Tag) extends Table[(Int, String, String, Timestamp)](tag, "CACHE") {

  def id: Column[Int] = column[Int]("SUP_ID", O.PrimaryKey, O.AutoInc)
  def url: Column[String] = column[String]("URL")
  def file: Column[String] = column[String]("FILE")
  def date: Column[Timestamp] = column[Timestamp]("CACHED_AT")

  def * = (id, url, file, date)
}
