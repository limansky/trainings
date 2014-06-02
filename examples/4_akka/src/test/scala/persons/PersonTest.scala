import akka.actor.{ ActorSystem, Actor, Props }
import akka.testkit.{ TestKit, TestProbe, ImplicitSender }
import org.scalatest.{ FlatSpecLike, BeforeAndAfterAll }
import scala.concurrent.duration._

class PersonTest(aSystem: ActorSystem) extends TestKit(aSystem) with ImplicitSender
          with FlatSpecLike with BeforeAndAfterAll {

  def this() = this(ActorSystem("PersonTestSystem"))

  override def afterAll = system.shutdown

  "Person" should "not response before start" in {
    system.actorOf(Props(classOf[Person], "Test")) ! Person.Introduce("Petr")
    expectNoMsg(500.millis)
  }

  it should "say something after start" in {
    val p = system.actorOf(Props(classOf[Person], "Test"))
    val probe = TestProbe()
    p ! Person.Start(List(p, probe.ref))
    probe.expectMsgAnyOf(5.seconds, Person.AskTime, Person.Introduce("Test"))
    probe.send(p, "Ok!")
  }

  it should "response on introduction" in {
    val p = system.actorOf(Props(classOf[Person], "Test"))
    p ! Person.Start(List(p, TestProbe().ref))
    p ! Person.Introduce("Petr")
    expectMsg("Nice to meet you, Petr! I'm Test.")
  }
}
