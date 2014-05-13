import akka.actor._

object Main extends App {
  val names = List("John", "Paul", "George", "Ringo")

  val system = ActorSystem("Beatles")

  val actors = names.map(name => system.actorOf(Props(classOf[Person], name)))

  actors foreach (_ ! Person.Start(actors))
}
