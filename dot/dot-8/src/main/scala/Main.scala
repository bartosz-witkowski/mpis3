package mpis3

import scala.quoted.*
import scala.quoted.staging
import scala.quoted.staging.Compiler
import Dot8._
import jdk.incubator.vector.{IntVector, VectorOperators}

object StagedDot {
  // Uncomment to debug the generated code
  //given settings: Compiler.Settings = Compiler.Settings.make(outDir = Some("/tmp/test"))
  //

  given Compiler = Compiler.make(StagedDot.getClass.getClassLoader)

  given StagedVectorized[Int] = new StagedVectorized[Int] {
    override def zero(using Quotes): Expr[Int] = Expr(0)
    override def plus(using Quotes): Expr[Int] => Expr[Int] => Expr[Int] = {
      l => r => '{ $l + $r }
    }
    override def times(using Quotes): Expr[Int] => Expr[Int] => Expr[Int] = {
      l => r => '{ $l * $r }
    }

    override type Vector = IntVector
    override def vectorType(using Quotes): Type[Vector] = Type.of[IntVector]

    override def vectorFromArray(using Quotes): Expr[Array[Int]] => Expr[Int] => Expr[Vector] = {
      array => offset => '{ IntVector.fromArray(IntVector.SPECIES_PREFERRED, $array, $offset) }
    }
    override def vectorLengthValue: Int = IntVector.SPECIES_PREFERRED.length
    override def vectorLength(using Quotes): Expr[Int] = '{ IntVector.SPECIES_PREFERRED.length }
    override def loopBound(using Quotes): Expr[Int] => Expr[Int] = {
      arraySize => '{ IntVector.SPECIES_PREFERRED.loopBound($arraySize) }
    }

    override def mul(using Quotes): Expr[Vector] => Expr[Vector] => Expr[Vector] = {
      l => r => '{ $l.mul($r) }
    }

    override def add(using Quotes): Expr[Vector] => Expr[Vector] => Expr[Vector] = {
      l => r => '{ $l.add($r) }
    }

    override def sum(using Quotes): Expr[Vector] => Expr[Int] = {
      v => '{ $v.reduceLanes(VectorOperators.ADD) }
    }
  }

  given StagedNumeric[Long] = new StagedNumeric[Long] {
    override def zero(using Quotes): Expr[Long] = Expr(0l)
    override def plus(using Quotes): Expr[Long] => Expr[Long] => Expr[Long] = {
      l => r => '{ $l + $r }
    }
    override def times(using Quotes): Expr[Long] => Expr[Long] => Expr[Long] = {
      l => r => '{ $l * $r }
    }
  }

  given StagedPromote[Int, Long] = new StagedPromote[Int, Long] {
    override def apply(using Quotes): Expr[Int] => Expr[Long] = { int =>
      '{ $int.toLong }
    }
  }

  def staged(left: Array[Int]): Array[Int] => Long = {
    val f = staging.run {
      val expr: Expr[Array[Int] => Long] = {
        '{
          val lhs: Array[Int] = ${Expr(left)}

          { (rhs : Array[Int]) =>
            ${
              Dot8.dotExpr[Int, Long](
                'lhs,
                'rhs)
            }
          }
        }
      }

      //println(expr.show)

      expr
    }

    f
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val f: Array[Int] => Long = StagedDot.staged(Array(1, 3, -5,  1, 3, -5,  1, 3))

    val r1 = f(Array(4, -2, -1, 4, -2, -1, 4, -2))
    val r2 = f(Array(-2, -1, 4, -2, -1, 4, -2, 4))

    println(r1 + ", " + r2)
  }
}
