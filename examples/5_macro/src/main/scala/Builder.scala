package builder

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

trait Builder[T] {
  def build: T
}

object BuilderMacro {
  def mkBuilder[T]: Builder[T] = macro mkBuilderImpl[T]

  def mkBuilderImpl[T: c.WeakTypeTag](c: Context): c.Expr[Builder[T]] = {
    import c.universe._
    val tpe = weakTypeOf[T]

    c.Expr[Builder[T]] {
      q"""
      new Builder[$tpe] {
        def build = ???
      }
    """
    }
  }
}
