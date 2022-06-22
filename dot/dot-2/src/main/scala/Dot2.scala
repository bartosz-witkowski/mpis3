package mpis3

import scala.quoted.*

/*
 * Dot product for a generic type A.
 *
 * Naive version just passing around functions.
 */
object Dot2 {
  inline def dot[A](
      l: Array[A], 
      r: Array[A], 
      zero: A,
      plus: (A, A) => A,
      times: (A, A) => A): A = {
    ${dotExpr('l, 'r, 'zero, 'plus, 'times)}
  }

  def dotExpr[A](
      l: Expr[Array[A]], 
      r: Expr[Array[A]],
      zero: Expr[A],
      plus: Expr[(A, A) => A],
      times: Expr[(A, A) => A])(using Quotes, Type[A]): Expr[A] = Log('{
    val lSize = $l.size
    val rSize = $r.size

    if (lSize == rSize) {
      ${ doDotExpr(l, r, zero, plus, times) }
    } else {
      throw new RuntimeException("Arrays must have the same size (got ${lSize} ${rSize})")
    }
  })

  private def doDotExpr[A](
      left: Expr[Array[A]], 
      right: Expr[Array[A]],
      zero: Expr[A],
      plus: Expr[(A, A) => A],
      times: Expr[(A, A) => A])(
      using q: Quotes,
      typeA: Type[A]): Expr[A] = '{
    var i = 0
    var sum = $zero

    while (i < ${left}.size) {
      val l = ${left}(i)
      val r = ${right}(i)

      sum = $plus(sum, $times(l, r))

      i += 1
    }

    sum
  }
}
