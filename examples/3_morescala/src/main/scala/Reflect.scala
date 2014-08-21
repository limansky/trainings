object Reflect extends App {
  def foo(item: Option[Any]) = item match {
    case t: Option[Int] => println("int")
    case t: Option[Boolean] => println("bool")
    case None         => println("none")
  }

  foo(Some(false))
}
