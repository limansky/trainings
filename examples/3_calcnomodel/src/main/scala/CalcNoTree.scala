import scala.util.parsing.combinator.JavaTokenParsers

trait SimpleCalc extends JavaTokenParsers {
  def num: Parser[Double] = floatingPointNumber ^^ (x => x.toDouble)
  def plus: Parser[Double => Double] = "+" ~> term ^^ (x => _ + x)
  def minus: Parser[Double => Double] = "-" ~> term ^^ (x => _ - x)
  def times: Parser[Double => Double] = "*" ~> factor ^^ (x => _ * x)
  def div: Parser[Double => Double] = "/" ~> factor ^^ (x => _ / x)
  def sum: Parser[Double] = term ~ rep(plus | minus) ^^ { case a ~ b => b.foldLeft(a)((i, f) => f(i)) }
  def term: Parser[Double] = factor ~ rep(times | div) ^^ { case a ~ b => b.foldLeft(a)((i, f) => f(i)) }
  def factor = num | "(" ~> sum <~ ")"
}

object CalcNoTree extends App with SimpleCalc {
  parseAll(sum, args.mkString) match {
    case Success(e, _) => println("Result: " + e)
    case fail: NoSuccess => println("Parsing failed: " + fail.msg)
  }
}
