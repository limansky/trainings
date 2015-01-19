trait LittleCalcParsers extends LittleParsers {

  val wspchars = " \t"

  def wspchar = char(c => wspchars.contains(c))
  def whitespace = rep(wspchar)

  def digit = char(_.isDigit)
  def num = (whitespace ~> rep1(digit)) <~ whitespace ^^ { case x => x.mkString.toInt }

  def plus: Parser[Int => Int] = char(_ == '+') ~> term ^^ (x => _ + x)
  def minus: Parser[Int => Int] = char(_ == '-') ~> term ^^ (x => _ - x)
  def times: Parser[Int => Int] = char(_ == '*') ~> factor ^^ (x => _ * x)
  def div: Parser[Int => Int] = char(_ == '/') ~> factor ^^ (x => _ / x)
  def sum: Parser[Int] = term ~ rep(plus | minus) ^^ { case a ~ b => b.foldLeft(a)((i, f) => f(i)) }
  def term: Parser[Int] = factor ~ rep(times | div) ^^ { case a ~ b => b.foldLeft(a)((i, f) => f(i)) }
  def factor = num | char(_ == '(') ~> sum <~ char(_ == ')')

  def parse(s: String) = sum(s.toList)
}

object LittleCalc extends App with LittleCalcParsers {
  val s = args.mkString
  parse(s) match {
    case Good(r, _) => println(s"Result is: $r")
    case Bad(m, _) => println(s"Cannot parse: $m")
  }
}
