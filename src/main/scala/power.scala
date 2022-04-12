package mpis3

import scala.quoted.*

/*
 * Adapted from:
 *
 * https://scala-lms.github.io/tutorials/02_basics.html
 */

object Power1 {
  inline def power(x: Double, inline n: Int) = ${powerExpr('x, 'n)}

  def powerExpr(x: Expr[Double], n: Expr[Int])(using Quotes): Expr[Double] = Log(
    powerExpr(x, n.valueOrAbort)
  )


  def powerExpr(x: Expr[Double], n: Int)(using Quotes): Expr[Double] =
    if n == 0 then '{ 1.0 } else '{ $x * ${ powerExpr(x, n - 1) } }
}


/*
 * With memoization
 */
object Power2 {
  inline def power(x: Double, inline n: Int) = ${powerExpr('x, 'n)}

  def powerExpr(x: Expr[Double], n: Expr[Int])(using Quotes): Expr[Double] = Log(
    powerExpr(x, n.valueOrAbort),
  )

  def powerExpr(x: Expr[Double], n: Int)(using Quotes): Expr[Double] = {
    if (n == 0) {
      '{ 1.0 } 
    } else if ((n & 1) == 0) '{ 
      val y = ${ powerExpr(x, n / 2) }
      y * y
    } else {
      '{ $x * ${ powerExpr(x, n - 1) } }
    }
  }
}
