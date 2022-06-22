package mpis3

import scala.quoted.*
import scala.reflect.ClassTag
import Instances.given

object Dot4 {
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
      using qctx: Quotes,
      typeA: Type[A],
      toExpr: ToExpr[Array[A]],
      sn: StagedNumeric[A]): Expr[A] = {

    '{
      val left = ${Expr(l)}
      val right = ${Expr(r)}

      var i = 0
      var sum: A = ${sn.zero}

      while (i < left.size) {
        val l: A = left(i)
        val r: A = right(i)

        sum = ${ 'sum + ('l * 'r) }

        i += 1
      }

      sum
    }
  }

  extension [A](l: Expr[A]) {
    def *(using q: Quotes, sn: StagedNumeric[A])(r: Expr[A]): Expr[A] = sn.times(l)(r)
    def +(using q: Quotes, sn: StagedNumeric[A])(r: Expr[A]): Expr[A] = sn.plus(l)(r)
  }

  abstract class StagedNumeric[A] {
    def zero(using Quotes): Expr[A]
    def plus(using Quotes): Expr[A] => Expr[A] => Expr[A]
    def times(using Quotes): Expr[A] => Expr[A] => Expr[A]
  }
}
