package jsonizer

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

trait Jsonable

object Jsonizer {
  implicit class Jsonized[T <: Jsonable](val data: T) extends AnyVal {
    def toJson: String = macro toJsonImpl[T]
  }

  def toJsonImpl[T: c.WeakTypeTag](c: Context): c.Expr[String] = {
    import c.universe._
    val tpe = weakTypeOf[T]

    c.Expr[String](Literal(Constant("{}")))
  }
}
