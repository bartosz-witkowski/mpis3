package mpis3

object Main {

  /*
  def todos(): Unit = {
    // Is there a way for this to not compile? 
    println(Dot1.dot(Array(1.0, 3.0, -5.0), Array(4.0, -2.0, -1.0)))
  }
  */

  import scala.quoted.staging
  import scala.quoted.staging.Compiler
  given Compiler = Compiler.make(Main.getClass.getClassLoader)

  def adtCompiler(): Unit = {
    import AdtCompiler._
    import Exp._
    import scala.quoted.*

    val exp = Plus(Plus(Num(2), Var("x")), Num(4))
    val letExp = Let("x", Num(3), exp)


    staging.run[Int] { 
      compileImpl(letExp)
    }
  }
  
  def dotProduct(): Unit = {
    println(Dot1.dot(Array(1.0, 3.0, -5.0), Array(4.0, -2.0, -1.0)))
    println(Dot2.dot[Double](
      Array(1.0, 3.0, -5.0), Array(4.0, -2.0, -1.0),
      0.0,
      _ + _,
      _ * _))

    {
      import scala.quoted.*
      import Dot3._

      given StagedNumeric[Double] = new StagedNumeric[Double] {
        override def zero(using Quotes): Expr[Double] = Expr(0.0)
        override def plus(using Quotes): Expr[(Double, Double) => Double] = '{ (l, r) => l + r }
        override def times(using Quotes): Expr[(Double, Double) => Double] = '{ (l, r) => l * r }
      }
      
      val r = staging.run[Double] { 
        Dot3.dotExpr[Double](
          Expr(Array(1.0, 3.0, -5.0)), 
          Expr(Array(4.0, -2.0, -1.0)))
      }

      println(r)
    }

    {
      import scala.quoted.*
      import Dot4._

      given StagedNumeric[Double] = new StagedNumeric[Double] {
        override def zero(using Quotes): Expr[Double] = Expr(0.0)
        override def plus(using Quotes): Expr[Double] => Expr[Double] => Expr[Double] = {
          l => r => '{ $l + $r }
        }
        override def times(using Quotes): Expr[Double] => Expr[Double] => Expr[Double] = {
          l => r => '{ $l * $r }
        }
      }

      
      val r = staging.run[Double] { 
        Dot4.dotExpr[Double](
          Expr(Array(1.0, 3.0, -5.0)), 
          Expr(Array(4.0, -2.0, -1.0)))
      }

      println(r)
    }

    {
      import scala.quoted.*
      import Dot5._

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
      
      val r = staging.run[Long] { 
        Dot5.dotExpr[Int, Long](
          Expr(Array(1, 3, -5)), 
          Expr(Array(4, -2, -1)))
      }

      println(r)
    }
  }

  def power(): Unit = {
    println(Power1.power(2, 8))
    println(Power2.power(2, 8))
  }
  
  def regex(): Unit = {
    // new RegexpMatcherTest()
    val matcher = new StagedRegexpMatcher {}
    import scala.quoted.*

    val regex: String => Boolean = staging.run[String => Boolean] {
      val staged: Expr[String => Boolean] = '{ (str: String) =>
        ${ matcher.matchsearch("a*b", 'str) }
        //${ matcher.matchsearch("hel*", 'str) }
      }

      println(staged.show)
      staged
    }
    
    //println(regex("yo hello"))
    println(regex("hello aab hello"))
  }

  def main(args: Array[String]): Unit = {
    power()
    dotProduct()
    adtCompiler()
    regex()
  }
}
