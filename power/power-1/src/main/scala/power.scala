package mpis3

import scala.quoted.*

object Power1 {
  inline def power(x: Double, inline n: Int) = ${powerExpr('x, 'n)}

  def powerExpr(x: Expr[Double], n: Expr[Int])(using Quotes): Expr[Double] = Log(
    powerExpr(x, n.valueOrAbort)
  )


  def powerExpr(x: Expr[Double], n: Int)(using Quotes): Expr[Double] =
    if n == 0 then '{ 1.0 } else '{ $x * ${ powerExpr(x, n - 1) } }
}
