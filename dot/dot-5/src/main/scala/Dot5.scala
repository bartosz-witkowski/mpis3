package mpis3

import scala.quoted.*
import scala.reflect.ClassTag
import Instances.given

object Dot5 {
  def dotExpr[A, B](
      l: Expr[Array[A]], 
      r: Expr[Array[A]])(using Quotes, Type[A], Type[B], StagedNumeric[A], FromExpr[A], ToExpr[A], ClassTag[A], StagedNumeric[B], StagedPromote[A, B]): Expr[B] = {
    val ll = l.valueOrAbort
    val rr = r.valueOrAbort

    if (ll.size == rr.size) {
      Log(doDotExpr(ll, rr))
    } else {
      throw new RuntimeException("Arrays must have the same size (got ${lSize} ${rSize})")
    }
  }

  private def doDotExpr[A, B](
      l: Array[A], 
      r: Array[A])(
      using qctx: Quotes,
      typeA: Type[A],
      typeB: Type[B],
      toExpr: ToExpr[Array[A]],
      na: StagedNumeric[A],
      nb: StagedNumeric[B],
      promote: StagedPromote[A, B]): Expr[B] = {
    '{
      val left = ${Expr(l)}
      val right = ${Expr(r)}

      var i = 0
      var sum: B = ${nb.zero}

      while (i < left.size) {
        val l: A = left(i)
        val r: A = right(i)

        sum = ${ 'sum + promote('l * 'r) }

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

  abstract class StagedPromote[A, B] {
    def apply(using Quotes): Expr[A] => Expr[B]
  }
}
