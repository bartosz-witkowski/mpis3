package mpis3

import scala.quoted.*

/*
 * Prints the staged code and its location
 */
object Log {
  def apply[A](a: Expr[A])(using Quotes): Expr[A] = {
    import quotes.reflect.*
    println("Compiled " + a.asTerm.pos)
    println(a.show)
    println()
    a
  }
}
