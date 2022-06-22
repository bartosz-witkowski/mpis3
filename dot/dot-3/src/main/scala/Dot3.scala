package mpis3

import scala.quoted.*
import scala.reflect.ClassTag
import Instances.given

/*
 * Dot product for a generic type A using typeclasses.
 */
object Dot3 {
  def dotExpr[A](
      l: Expr[Array[A]], 
      r: Expr[Array[A]])(using Quotes, Type[A], FromExpr[A], ToExpr[A], ClassTag[A], StagedNumeric[A]): Expr[A] = {
    val ll = l.valueOrAbort
    val rr = r.valueOrAbort

    if (ll.size == rr.size) {
      Log(doDotExpr(ll, rr))
    } else {
      throw new RuntimeException("Arrays must have the same size (got ${lSize} ${rSize})")
    }
  }


  private def doDotExpr[A](
      l: Array[A], 
      r: Array[A])(
      using q: Quotes,
      typeA: Type[A],
      toExpr: ToExpr[Array[A]],
      sn: StagedNumeric[A]): Expr[A] = '{
    val left = ${Expr(l)}
    val right = ${Expr(r)}

    var i = 0
    var sum = ${sn.zero}

    while (i < left.size) {
      val l = left(i)
      val r = right(i)

      sum = ${sn.plus}(sum, ${sn.times}(l, r))

      i += 1
    }

    sum
  }

  abstract class StagedNumeric[A] {
    def zero(using Quotes): Expr[A]
    def plus(using Quotes): Expr[(A, A) => A]
    def times(using Quotes): Expr[(A, A) => A]
  }
}
