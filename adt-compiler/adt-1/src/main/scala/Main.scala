package mpis3

import scala.quoted.staging
import scala.quoted.staging.Compiler

object Main {
  given Compiler = Compiler.make(Main.getClass.getClassLoader)

  def main(args: Array[String]): Unit = {
    import AdtCompiler1._
    import Exp._
    import scala.quoted.*

    val exp = Plus(Plus(Num(2), Var("x")), Num(4))
    val letExp = Let("x", Num(3), exp)

    staging.run[Int] { 
      compileImpl(letExp)
    }
  }
}
