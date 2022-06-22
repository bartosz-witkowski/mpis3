package mpis3

import scala.quoted.*
import scala.quoted.staging
import scala.quoted.staging.Compiler
import Dot5._

object Main {
  given Compiler = Compiler.make(Main.getClass.getClassLoader)

  given StagedNumeric[Int] = new StagedNumeric[Int] {
    override def zero(using Quotes): Expr[Int] = Expr(0)
    override def plus(using Quotes): Expr[Int] => Expr[Int] => Expr[Int] = {
      l => r => '{ $l + $r }
    }
    override def times(using Quotes): Expr[Int] => Expr[Int] => Expr[Int] = {
      l => r => '{ $l * $r }
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

  def main(args: Array[String]): Unit = {
    val r = staging.run[Long] { 
      Dot5.dotExpr[Int, Long](
        Expr(Array(1, 3, -5)), 
        Expr(Array(4, -2, -1)))
    }

    println(r)
  }
}
