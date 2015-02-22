import scala.language.implicitConversions

trait LittleParsers {

  type Reader = List[Char]

  sealed abstract class Result[+T]
  case class Good[+T](r: T, rest: Reader) extends Result[T]
  case class Bad(msg: String, rest: Reader) extends Result[Nothing]

  case class ~[+A, +B](a: A, b: B)

  trait Parser[+T] extends (Reader => Result[T]) { p =>

    def ~[U](u: => Parser[U]): Parser[T ~ U] = new Parser[T ~ U] {
      def apply(r: Reader) = p(r) match {
        case Good(x, r1) => u(r1) match {
          case Good(y, r2) => Good(new ~(x, y), r2)
          case bad: Bad => bad
        }
        case bad: Bad => bad
      }
    }

    def ^^[U](f: T => U): Parser[U] = new Parser[U] {
      def apply(r: Reader) = p(r) match {
        case Good(x, rest) => Good(f(x), rest)
        case bad: Bad => bad
      }
    }

    def ~>[U](u: => Parser[U]): Parser[U] = p ~ u ^^ { case _ ~ y => y }

    def <~[U](u: => Parser[U]): Parser[T] = p ~ u ^^ { case x ~ _ => x }

    def |[U >: T](u: => Parser[U]): Parser[U] = new Parser[U] {
      def apply(r: Reader) = p(r) match {
        case g: Good[_] => g
        case _ => u(r)
      }
    }
  }

  def char(f: Char => Boolean): Parser[Char] = new Parser[Char] {
    def apply(r: Reader) = r match {
      case x :: xs => if (f(x)) Good(x, xs) else Bad(s"Unexpected char '$x'", r)
      case Nil => Bad("Expected char, but got end of input", Nil)
    }
  }

  def good[T](t: T): Parser[T] = new Parser[T] {
    def apply(r: Reader) = Good(t, r)
  }

  def rep[T](p: Parser[T]): Parser[List[T]] = new Parser[List[T]] {
    import scala.annotation.tailrec

    def apply(r: Reader) = {
      @tailrec
      def process(d: List[T], r: Reader): (List[T], Reader) = p(r) match {
        case Good(x, r1) => process(x :: d, r1)
        case _ => (d, r)
      }

      val (l, rest) = process(Nil, r)
      Good(l.reverse, rest)
    }
  }

  def rep1[T](p: Parser[T]): Parser[List[T]] = new Parser[List[T]] {
    def apply(r: Reader) = p(r) match {
      case Good(x, r1) => rep(p)(r1) match {
        case Good(xs, rest) => Good(x :: xs, rest)
        case bad: Bad => bad
      }
      case bad: Bad => bad
    }
  }

  def rep1sep[T, U](p: => Parser[T], u: => Parser[U]) =
    p ~ rep(u ~> p) ^^ { case x ~ xs => x :: xs }

  def repsep[T, U](p: => Parser[T], u: => Parser[U]) =
    rep1sep(p, u) | good(Nil)

  implicit def acceptChar(c: Char) = char(_ == c)

  implicit def acceptString(s: String): Parser[List[Char]] =
    s.map(c => acceptChar(c) ^^ (x => List(x)))
      .reduceLeft(_ ~ _ ^^ { case x ~ y => x ++ y })
}
