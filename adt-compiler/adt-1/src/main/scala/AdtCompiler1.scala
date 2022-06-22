package mpis3

import scala.quoted.*

/*
 * From: https://dotty.epfl.ch/docs/reference/metaprogramming/macros.html#lifting-expressions
 */
object AdtCompiler1 {
  enum Exp {
    case Num(n: Int)
    case Plus(e1: Exp, e2: Exp)
    case Var(x: String)
    case Let(x: String, e: Exp, in: Exp)
  }

  import Exp._
  
  def compileImpl(e: Exp)(using Quotes): Expr[Int] = Log {
    compileImpl(e, Map.empty)
  }

  def compileImpl(e: Exp, env: Map[String, Expr[Int]])(using Quotes): Expr[Int] = {
    e match
      case Num(n) =>
        Expr(n)
      case Plus(e1, e2) =>
        '{ ${ compileImpl(e1, env) } + ${ compileImpl(e2, env) } }
      case Var(x) =>
        env(x)
      case Let(x, e, body) =>
        '{ val y = ${ compileImpl(e, env) }; ${ compileImpl(body, env + (x -> 'y)) } }
  }
}
