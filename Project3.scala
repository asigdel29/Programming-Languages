import scala.io.StdIn.readLine
import scala.util.matching.Regex

abstract class S {
  def matching(input: S): Boolean
}

abstract class Terminal extends S
case class E(l: T, right: Option[E2]) extends S {
  def matching(input: S): Boolean = {
    if (right.isEmpty) {
      input match {
        case E(t,None) => l.matching(t)
        case t: T => l.matching(t)
        case t: T2 => l.matching(t)
        case F(a,None) => l.matching(a)
        case a: A => l.matching(a)
        case _ => false
      }
    } else {
      input match {
        case E(t,None) => l.matching(t) || right.get.l.matching(t)
        case t: T => l.matching(t) || right.get.l.matching(t)
        case t: T2 => l.matching(t) || right.get.l.matching(t)
        case F(a,None) => l.matching(a) || right.get.l.matching(a)
        case a: A => l.matching(a) || right.get.l.matching(a)
        case _ => false
      }
    }
  }
}
case class E2(l: E3) extends S {
  def matching(input: S): Boolean = input match {
    case E(t,None) => l.matching(t)
    case t: T => l.matching(t)
    case t: T2 => l.matching(t)
    case F(a,None) => l.matching(a)
    case a: A => l.matching(a)
    case _ => false
  }
}
case class E3(l: T, right: Option[E2]) extends S {
  def matching(input: S): Boolean = {
    if (right.isEmpty) {
      input match {
        case E(t,None) => l.matching(t)
        case t: T => l.matching(t)
        case t: T2 => l.matching(t)
        case F(a,None) => l.matching(a)
        case a: A => l.matching(a)
        case _ => false
      }
    } else {
      input match {
        case E(t,None) => l.matching(t) || right.get.l.matching(t)
        case t: T => l.matching(t) || right.get.l.matching(t)
        case t: T2 => l.matching(t) || right.get.l.matching(t)
        case F(a,None) => l.matching(a) || right.get.l.matching(a)
        case a: A => l.matching(a) || right.get.l.matching(a)
        case _ => false
      }
    }
  }
}
case class T(l: F, right: Option[T2]) extends S {
  def matching(input: S): Boolean = {
    val thisT2 = T2(l, right)
    if (right.isEmpty) {
      input match {
        case t: T2 => l.matching(t)
        case t: T => l.matching(t)
        case F(a, None) => l.matching(a)
        case a: A => l.matching(a)
        case _ => false
      }
    } else {
      if (l.right.isEmpty) {
        if (right.get.required) {
          input match {
            case t@T2(_, Some(_)) => thisT2.traverseMatch(None, Some(t))
            case t@T(_, Some(_)) => thisT2.traverseMatch(None, Some(T2(t.l, t.right)))
            case _ => false
          }
        } else {
          input match {
            case t@T2(f, Some(t2)) =>
              l.matching(t) || thisT2.traverseMatch(None, Some(t))
            case t@T(f, Some(t2)) =>
              l.matching(t) || thisT2.traverseMatch(None, Some(T2(t.l, t.right)))
            case T2(f, None) => l.matching(f)
            case T(f, None) => l.matching(f)
            case F(a, None) => l.matching(a)
            case a: A => l.matching(a)
            case _ => false
          }
        }
      } else {
        if (right.get.required) {
          input match {
            case t@T2(f, Some(t2)) =>
              right.get.matching(t) || thisT2.traverseMatch(None, Some(t))
            case t@T(f, Some(t2)) =>
              right.get.matching(t) || thisT2.traverseMatch(None, Some(T2(t.l, t.right)))
            case T2(f, None) => right.get.matching(f)
            case T(f, None) => right.get.matching(f)
            case F(a, None) => right.get.matching(a)
            case a: A => right.get.matching(a)
            case _ => false
          }
        } else {
          input match {
            case t@T2(f, Some(t2)) =>
              l.matching(t) || right.get.matching(t) || thisT2.traverseMatch(None, Some(t))
            case t@T(f, Some(t2)) =>
              l.matching(t) || right.get.matching(t) || thisT2.traverseMatch(None, Some(T2(t.l, t.right)))
            case T2(f, None) => l.matching(f) || right.get.matching(f)
            case T(f, None) => l.matching(f) || right.get.matching(f)
            case F(a, None) => l.matching(a) || right.get.matching(a)
            case a: A => l.matching(a) || right.get.matching(a)
            case _ => false
          }
        }
      }
    }
  }
  def push(t2: T2): Option[T2] = this match {
    case T(f, None) => Some(T2(f, Some(t2)))
    case T(f, Some(t)) => Some(T2(f, Some(t.push(t2))))
  }
}
case class T2(l: F, right: Option[T2]) extends S {
  def matching(input: S): Boolean = {
    if (right.isEmpty) {
      input match {
        case t: T => l.matching(t)
        case t: T2 => l.matching(t)
        case F(a, None) => l.matching(a)
        case a: A => l.matching(a)
        case _ => false
      }
    } else {
      if (l.right.isEmpty) {
        if (right.get.required) {
          input match {
            case t@T2(_, Some(_)) => this.traverseMatch(None, Some(t))
            case t@T(_, Some(_)) => this.traverseMatch(None, Some(T2(t.l, t.right)))
            case _ => false
          }
        } else {
          input match {
            case t@T2(f, Some(t2)) =>
              l.matching(t) || this.traverseMatch(None, Some(t))
            case t@T(f, Some(t2)) =>
              l.matching(t) || this.traverseMatch(None, Some(T2(t.l, t.right)))
            case T(f, None) => l.matching(f)
            case T2(f, None) => l.matching(f)
            case F(a, None) => l.matching(a)
            case a: A => l.matching(a)
            case _ => false
          }
        }
      } else {
        if (right.get.required) {
          input match {
            case t@T2(f, Some(t2)) =>
              right.get.matching(t) || this.traverseMatch(None, Some(t))
            case t@T(f, Some(t2)) =>
              right.get.matching(t) || this.traverseMatch(None, Some(T2(t.l, t.right)))
            case T(f, None) => right.get.matching(f)
            case T2(f, None) => right.get.matching(f)
            case F(a, None) => right.get.matching(a)
            case a: A => right.get.matching(a)
            case _ => false
          }
        } else {
          input match {
            case t@T2(f, Some(t2)) =>
              l.matching(t) || right.get.matching(t) || this.traverseMatch(None, Some(t))
            case t@T(f, Some(t2)) =>
              l.matching(t) || right.get.matching(t) || this.traverseMatch(None, Some(T2(t.l, t.right)))
            case T(f, None) => l.matching(f) || right.get.matching(f)
            case T2(f, None) => l.matching(f) || right.get.matching(f)
            case F(a, None) => l.matching(a) || right.get.matching(a)
            case a: A => l.matching(a) || right.get.matching(a)
            case _ => false
          }
        }
      }
    }
  }

  def required: Boolean = this match {
    case T2(f, Some(t2)) => f.right.isEmpty || t2.required
    case T2(f, None) => f.right.isEmpty
  }
  def push(t2: T2): T2 = this match {
    case T2(f, None) => T2(f, Some(t2))
    case T2(f, Some(t)) => T2(f, Some(t.push(t2)))
  }

  def traverseMatch(prev: Option[T2], t2opt: Option[T2]): Boolean = {
    val newl = T2(l, None)
    if (prev.isEmpty) {
      t2opt match {
        case Some(T2(f1, Some(T2(f, None)))) =>
          newl.matching(T2(f1, None)) && right.get.matching(f)
        case Some(T2(f1, t)) =>
          val newPrev = Some(T2(f1, None))
          newl.matching(newPrev.get) && right.get.matching(t.get) ||
            this.traverseMatch(newPrev, t)
        case _ => false
      }
    } else {
      t2opt match {
        case Some(T2(f1, Some(T2(f, None)))) =>
          val current = prev.get.push(T2(f1, None))
          val t = T2(f, None)
          newl.matching(current) && right.get.matching(t)
        case Some(T2(f1, Some(T2(f, t2)))) =>
          val current = Some(prev.get.push(T2(f1, None)))
          val t = Some(T2(f, t2))
          newl.matching(current.get) && right.get.matching(t.get) ||
            this.traverseMatch(current, t)
        case _ => false
      }
    }
  }
}

case class F(l: A, right: Option[F2]) extends S {
  def matching(input: S): Boolean = {
    input match {
      case F(a, None) => l.matching(a)
      case t: T => l.matching(t)
      case t: T2 => l.matching(t)
      case a: A => l.matching(a)
      case _ => false
    }
  }
}


case class F2(l: Option[F2]) extends S {
  def matching(input: S): Boolean = false
}

abstract class A extends S

case class C(l: String) extends A {
  def matching(input: S): Boolean = input match {
    case T(f,None) => this.matching(f)
    case T2(f,None) => this.matching(f)
    case F(a,None) => this.matching(a)
    case C(c) => l == "." || l == c
    case _ => false
  }
}
case class A2(l: E) extends A {
  def matching(input: S): Boolean = input match {
    case t: T => l.matching(t)
    case t: T2 => l.matching(t)
    case f: F => l.matching(f)
    case a: A => l.matching(a)
    case _ => false
  }
}
class RecursiveDescentParser(input: String) {
  var index = 0
  val Regex: Regex = "^[0-9a-zA-Z. ]".r
  def parseS(): S = parseE()
  def parseE(): E = E(parseT(), parseE2())
  def parseE2(): Option[E2] = {
    if (index < input.length && input(index) == '|') {
      index += 1
      Some(E2(parseE3()))
    } else None
  }
  def parseE3(): E3 = E3(parseT(), parseE2())
  def parseT(): T = T(parseF(), parseT2())
  def parseT2(): Option[T2] = {
    if (index < input.length &&
      (input(index) == '(' || input(index).isLetterOrDigit || input(index) == ' ' || input(index) == '.')) {
      Some(T2(parseF(), parseT2()))
    } else None
  }
  def parseF(): F = {
    if (index < input.length && input(index) == '(') {
      F(parseA(), parseF21())
    } else F(parseA(), parseF2())
  }
  def parseF2(): Option[F2] = {
    if (index < input.length && input(index) == '?') {
      index += 1
      Some(F2(parseF2()))
    } else None
  }
  def parseF21(): Option[F2] = {
    index += 1
    parseF2()
  }
  def parseA(): A = {
    if (input(index) == '(') {
      index += 1
      parseA2()
    } else {
      val currStr = input.substring(index)
      val chars = Regex.findAllIn(currStr)
      val char = chars.next()
      index += char.length()
      C(char)
    }
  }
  def parseA2(): A2 = A2(parseE())
}

object Main {
  def main(args: Array[String]): Unit = {
    val patternInput = readLine("pattern? ")
    val patternParser = new RecursiveDescentParser(patternInput)
    val pattern = patternParser.parseS()
    println(pattern)
    var stringInput = readLine("string? ")

    while (stringInput != "/") {
      if (stringInput.isEmpty) {
        println("no match")
        stringInput = readLine("string? ")
      } else {
        val stringParser = new RecursiveDescentParser(stringInput)
        val parsedStr = stringParser.parseS()
        println(parsedStr)
        val isMatch = pattern.matching(parsedStr)
        if (isMatch) {
          println("match")
        } else println("no match")
        stringInput = readLine("string? ")
      }
    }
  }
}