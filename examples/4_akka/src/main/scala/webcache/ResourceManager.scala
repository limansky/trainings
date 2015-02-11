package webcache

import scala.slick.lifted.TableQuery
import scala.slick.driver.H2Driver.simple._
import java.sql.Date

object ResourceManager {
  val db = Database.forURL("jdbc:h2:mem:webcache", driver = "org.h2.Driver")
  
  object cache extends TableQuery(new Cache(_)) {
    val byId = this.findBy(_.id)
  } 
  
  def toResource(c: (Int, String, String, Date)): Resource = {
    Resource(c._1, c._2, c._4.getTime)
  }
  
  def getResources(): List[Resource] = {
    db.withSession { implicit s => 
      cache.list map toResource
    }
  }
  
  def getById(id: Int): Option[Resource] = {
    db.withSession { implicit s => 
      cache.byId(id).firstOption map toResource
    }
  }
  
  def delete(id: Int): Boolean = {
    db.withSession { implicit s =>
      cache.byId(id).firstOption match {
        case Some(c) => 
          cache.byId(id).delete
          true
        case None => false
      }
    }
  }
}
