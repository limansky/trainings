import akka.actor._
import akka.util.Timeout
import scala.util.Random
import scala.concurrent.duration._
import java.util.Date

object Person {
  case class Start(left: List[ActorRef])
  case class Introduce(name: String)
  case class Bye(who: ActorRef)
  case object SaySomething
  case object AskTime
}

class Person(name: String) extends Actor {

  import Person._
  import context.dispatcher
  import akka.pattern.ask
  
  implicit val timeout = Timeout(100.millis)

  def receive = {
    case Start(lst) =>
      val d = Random.nextInt(5)
      context.system.scheduler.scheduleOnce(d.seconds, self, SaySomething)
      context become speaking(3, lst.toSet - self)
  }

  def speaking(c: Int, persons: Set[ActorRef]): Receive = {
    case Introduce(n) => 
      sender ! s"Nice to meet you, $n! I'm $name."

    case AskTime =>
      val time = new Date
      sender ! s"I'm $name, it's $time."

    case Bye(a) =>
      val left = persons - a
      if (left.isEmpty) 
        context.system.shutdown
      else
        context become speaking(c, left)

    case SaySomething =>
      if (c > 0) {
        val msg = if (Random.nextBoolean) {
          println(s"Hello! My name is $name.")
          Introduce(name)
        } else {
          println(s"I'm $name. What time is it?")
          AskTime
        }
        val a = persons.toList(Random.nextInt(persons.size))
        val f = a ? msg
        f.onSuccess { 
          case reply => 
            println(reply)
            val d = Random.nextInt(5)
            context.system.scheduler.scheduleOnce(d.seconds, self, SaySomething)
            context become speaking(c - 1, persons)
        }
      } else {
        println(s"Bye-bye from $name")
        persons foreach (_ ! Bye(self))
        context.stop(self)
      }
  }
}
