package mpis3

import scala.quoted.*
import scala.reflect.ClassTag
import Instances.given

object Dot8 {
  sealed abstract class StagedValue[A] {
    import StagedValue.{Known, Unknown}

    def expr: Expr[A]
    def value: Option[A]

    def orElse(that: StagedValue[A]): StagedValue[A] = this match {
      case k: Known[A] =>
        this
      case _: Unknown[A] =>
        that
    }

    def isUnknown: Boolean = this match {
      case u: Unknown[A] =>
        true
      case _ =>
        false
    }
  }
  object StagedValue {
    def apply[A](expr: Expr[A])(using Quotes, FromExpr[A]): StagedValue[A] = {
      expr.value match {
        case Some(value) =>
          Known(value, expr)
        case None =>
          Unknown(expr)
      }
    }

    case class Known[A](knownValue: A, expr: Expr[A]) extends StagedValue[A] {
      def value = Some(knownValue)
    }
    case class Unknown[A](expr: Expr[A]) extends StagedValue[A] {
      def value = None
    }
  }

  def dotExpr[A, B](
        l: Expr[Array[A]], 
        r: Expr[Array[A]])(using Quotes, Type[A], FromExpr[A], ToExpr[Array[A]], ToExpr[A], ClassTag[A], Type[B], StagedVectorized[A], StagedNumeric[B], StagedPromote[A, B]): Expr[B] = {
      // does this even make sense? we would ideally want to call it for a known l and many unknown r-s?
      val lArray: StagedValue[Array[A]] = StagedValue(l)
      val rArray: StagedValue[Array[A]] = StagedValue(r)
      
      doDotExpr(lArray, rArray)
    }

  private def doDotExpr[A, B](
      leftStaged: StagedValue[Array[A]], 
      rightStaged: StagedValue[Array[A]])(
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
    
    val sizeOption = leftStaged.value.map(_.size) orElse rightStaged.value.map(_.size)

    '{
      val left: Array[A] = ${leftStaged.expr}
      val right: Array[A] = ${rightStaged.expr}
      var i = 0
      var sum: B = ${nb.zero}

      ${ 
        if (leftStaged.isUnknown || rightStaged.isUnknown) '{
          require(left.size == right.size)
        } else '{
        }
      }
      ${
        if (sizeOption.map(_ >= vectorLength).getOrElse(true)) '{
          val bound: Int = ${na.loopBound('{ ${leftStaged.expr}.size }) }
          
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
        @inline def sizeBound: Expr[Int] = sizeOption match {
          case Some(size) =>
            Expr(size)
          case None =>
            '{left.size}
        }

        if (sizeOption.map(_ % vectorLength != 0).getOrElse(true)) '{
          while (i < $sizeBound) {
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
