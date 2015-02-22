import scala.util.parsing.combinator.RegexParsers
import scala.io.Source

abstract class Cell
case class NumCell(number: Double) extends Cell
case class StringCell(string: String) extends Cell

trait CSVParser extends RegexParsers {

  override def skipWhitespace = false
  def lf = "\n"
  def space = "[ \t]*".r
  def number: Parser[NumCell] = space ~> """-?\d+(\.\d*)?""".r <~ space ^^ { x => NumCell(x.toDouble) }
  def string: Parser[StringCell] = """[^,\n]*""".r ^^ { StringCell }

  def cell: Parser[Cell] = number | string
  def line: Parser[List[Cell]] = repsep(cell, ",") <~ lf
  def file: Parser[List[List[Cell]]] = rep(line)
}

object FileParser extends App with CSVParser {

  val str = Source.fromFile(args.head).mkString
  parseAll(file, str) match {
    case Success(result, _) => result foreach (l => println(l.mkString("\t")))
    case fail: NoSuccess => println("Parsing failed" + fail.msg)
  }
}
