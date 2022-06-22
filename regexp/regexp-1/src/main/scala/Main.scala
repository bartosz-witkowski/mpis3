package mpis3

import scala.quoted.staging
import scala.quoted.staging.Compiler

object Main {
  given Compiler = Compiler.make(Main.getClass.getClassLoader)

  def main(args: Array[String]): Unit = {
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
}
