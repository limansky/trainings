import scala.reflect.runtime.universe._

object Reflect extends App {
  def foo[T: TypeTag](item: Option[T]) = {
    val tt = typeOf[T]
    item match {
      case t if tt =:= typeOf[Boolean] => println("a bool ")
      case t if tt =:= typeOf[Int]     => println("an int ")
      case t if tt <:< typeOf[Seq[_]] => println(s"a sequence ($tt)")
    }
  }

  foo(Some(5))
  foo(Some(false))
  foo(Some(List(3,4)))
}
