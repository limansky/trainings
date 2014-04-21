import scala.util.parsing.combinator.JavaTokenParsers

abstract class Expr {
  val eval: Double
}

case class Sum(a: List[Expr]) extends Expr {
  override val eval = a.foldLeft(0.0)(_ + _.eval)
}

case class Mul(a: List[Expr]) extends Expr {
  override val eval = a.foldLeft(1.0)(_ * _.eval)
}

case class Num(a: Double) extends Expr {
  override val eval = a
}

trait CalcParser extends JavaTokenParsers {
  def num = floatingPointNumber ^^ (s => Num(s.toDouble))
  def sum: Parser[Sum] = term ~ rep("+" ~> term) ^^ { case a ~ b => Sum(a :: b) }
  def mul = factor ~ rep("*" ~> factor) ^^ { case a ~ b => Mul(a :: b) }
  def factor = num | "(" ~> sum <~ ")"
  def term = mul | num
}

object Calculator extends App with CalcParser {
  parseAll(sum, args.mkString) match {
    case Success(e, _) => println("Result: " + e.eval)
    case fail: NoSuccess    => println("Parsing failed: " + fail.msg)
  }
}
