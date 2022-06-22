package mpis3

import scala.quoted.*
import scala.quoted.staging
import scala.quoted.staging.Compiler
import Dot4._

object Main {
  given Compiler = Compiler.make(Main.getClass.getClassLoader)

  given StagedNumeric[Double] = new StagedNumeric[Double] {
    override def zero(using Quotes): Expr[Double] = Expr(0.0)
    override def plus(using Quotes): Expr[Double] => Expr[Double] => Expr[Double] = {
      l => r => '{ $l + $r }
    }
    override def times(using Quotes): Expr[Double] => Expr[Double] => Expr[Double] = {
      l => r => '{ $l * $r }
    }
  }

  def main(args: Array[String]): Unit = {
    val r = staging.run[Double] { 
      Dot4.dotExpr[Double](
        Expr(Array(1.0, 3.0, -5.0)), 
        Expr(Array(4.0, -2.0, -1.0)))
    }

    println(r)
  }
}
