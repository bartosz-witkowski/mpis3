package mpis3

import scala.quoted.*
import scala.reflect.ClassTag
import Instances.given

object Dot7 {
  def dotExpr[A, B](
        l: Expr[Array[A]], 
        r: Expr[Array[A]])(using Quotes, Type[A], FromExpr[A], ToExpr[Array[A]], ToExpr[A], ClassTag[A], Type[B], StagedVectorized[A], StagedNumeric[B], StagedPromote[A, B]): Expr[B] = {
      // does this even make sense? we would ideally want to call it for a known l and many unknown r-s?
      val lArray: Array[A] = l.valueOrAbort
      val rArray: Array[A] = r.valueOrAbort
      
      if (lArray.size != rArray.size) {
        throw new RuntimeException("array sizes must match")
      } else {
        Log(doDotExpr(lArray, rArray))
      }
    }

  private def doDotExpr[A, B](
      _left: Array[A], 
      _right: Array[A])(
      using qctx: Quotes,
      typeA: Type[A],
      typeB: Type[B],
      toExprArrA: ToExpr[Array[A]],
      toExprA: ToExpr[A],
      na: StagedVectorized[A],
      nb: StagedNumeric[B],
      promote: StagedPromote[A, B]): Expr[B] = {
    given vectorType: Type[na.Vector] = na.vectorType

    val vectorLength = na.vectorLengthValue

    '{
      val left: Array[A] = ${Expr(_left)}
      val right: Array[A] = ${Expr(_right)}
      var i = 0
      var sum: B = ${nb.zero}

      ${ 
        if (_left.size > vectorLength) '{
          val bound: Int = ${na.loopBound(Expr(_left.length))}

          while (i < bound) {
            val l: na.Vector = ${na.vectorFromArray('left)('i)}
            val r: na.Vector = ${na.vectorFromArray('right)('i)}
            
            val vmul = ${na.mul('l)('r)}
            sum = ${ 'sum + promote(na.sum('vmul)) }

            i += ${ na.vectorLength }
          }
        } else '{
        }
      }
      ${
        if (_left.size % vectorLength == 0) '{
          while (i < ${Expr(_left.size)}) {
            val l: A = left(i)
            val r: A = right(i)

            sum = ${ 'sum + promote('l * 'r) }

            i += 1
          }
        } else '{
        }
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

  abstract class StagedVectorized[A] extends StagedNumeric[A] {
    type Vector
    def vectorType(using Quotes): Type[Vector]

    def vectorLengthValue: Int
    def vectorLength(using Quotes): Expr[Int]
    def vectorFromArray(using Quotes): Expr[Array[A]] => Expr[Int] => Expr[Vector]
    def loopBound(using Quotes): Expr[Int] => Expr[Int]
    def mul(using Quotes): Expr[Vector] => Expr[Vector] => Expr[Vector]
    def add(using Quotes): Expr[Vector] => Expr[Vector] => Expr[Vector]
    def sum(using Quotes): Expr[Vector] => Expr[A]
  }

  abstract class StagedPromote[A, B] {
    def apply(using Quotes): Expr[A] => Expr[B]
  }
}
