package mpis3

import scala.quoted.*

object Dot1 {
  inline def dot(l: Array[Double], r: Array[Double]): Double = {
    ${dotExpr('l, 'r)}
  }

  def dotExpr(l: Expr[Array[Double]], r: Expr[Array[Double]])(using Quotes): Expr[Double] = Log('{
    val lSize = $l.size
    val rSize = $r.size

    if (lSize == rSize) {
      ${ doDotExpr(l, r) }
    } else {
      throw new RuntimeException("Arrays must have the same size (got ${lSize} ${rSize})")
    }
  })

  def doDotExpr(l: Expr[Array[Double]], r: Expr[Array[Double]])(using Quotes): Expr[Double] = '{
    var i = 0
    var sum: Double = 0

    while (i < ${l}.size) {
      sum += ${l}(i) * ${r}(i)
      i += 1
    }

    sum
  }
}
