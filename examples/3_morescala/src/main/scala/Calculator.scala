import scala.util.parsing.combinator.JavaTokenParsers

abstract class Expr {
  val eval: Double
}

case class Add(a: Expr, b: Expr) extends Expr {
  override val eval = a.eval + b.eval
}

case class Sub(a: Expr, b: Expr) extends Expr {
  override val eval = a.eval - b.eval
}

case class Mul(a: Expr, b: Expr) extends Expr {
  override val eval = a.eval * b.eval
}

case class Div(a: Expr, b: Expr) extends Expr {
  override val eval = a.eval / b.eval
}

case class Num(a: Double) extends Expr {
  override val eval = a
}

trait CalcParser extends JavaTokenParsers {
  def num = floatingPointNumber ^^ (s => Num(s.toDouble))
  def plus: Parser[Expr => Expr] = "+" ~> term ^^ (x => Add(_, x))
  def minus: Parser[Expr => Expr] = "-" ~> term ^^ (x => Sub(_, x))
  def times: Parser[Expr => Expr] = "*" ~> factor ^^ (x => Mul(_, x))
  def div: Parser[Expr => Expr] = "/" ~> factor ^^ (x => Div(_, x))
  def sum: Parser[Expr] = term ~ rep(plus | minus) ^^ { case a ~ b => b.foldLeft(a)((i, f) => f(i)) }
  def term: Parser[Expr] = factor ~ rep(times | div) ^^ { case a ~ b => b.foldLeft(a)((i, f) => f(i)) }
  def factor = num | "(" ~> sum <~ ")"
}

object Calculator extends App with CalcParser {
  parseAll(sum, args.mkString) match {
    case Success(e, _) => println("Result: " + e.eval)
    case fail: NoSuccess    => println("Parsing failed: " + fail.msg)
  }
}
