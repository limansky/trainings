package webcache

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.ProvenShape
import java.sql.Date

class Cache(tag: Tag) extends Table[(Int, String, String, Date)](tag, "CACHE") {

  def id: Column[Int] = column[Int]("SUP_ID", O.PrimaryKey)
  def url: Column[String] = column[String]("URL")
  def file: Column[String] = column[String]("FILE")
  def date: Column[Date] = column[Date]("CACHED_AT")

  def * = (id, url, file, date)
}
