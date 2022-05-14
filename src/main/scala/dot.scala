package mpis3

import scala.quoted.*

/*
 * Inspired by: 
 *
 *   https://github.com/nicolasstucki/scala-metaprogramming-exercise/blob/master/src/main/scala/Vectors.scala
 *
 */

/*
 * Simple dot product
 */
object Dot1 {
  inline def dot(l: Array[Double], r: Array[Double]): Double = {
    ${dotExpr('l, 'r)}
  }

  def dotExpr(l: Expr[Array[Double]], r: Expr[Array[Double]])(using Quotes): Expr[Double] = Log('{
    val lSize = $l.size
    val rSize = $r.size

    if (lSize == rSize) {
      ${ doDotExpr(l, r) }
    } else {
      throw new RuntimeException("Arrays must have the same size (got ${lSize} ${rSize})")
    }
  })

  def doDotExpr(l: Expr[Array[Double]], r: Expr[Array[Double]])(using Quotes): Expr[Double] = '{
    val left = $l
    val right = $r

    var i = 0
    var sum: Double = 0

    while (i < left.size) {
      sum += (left(i) * right(i))
      i += 1
    }

    sum
  }
}

/*
 * Dot product for a generic type A.
 *
 * Naive version just passing around functions.
 */
object Dot2 {
  inline def dot[A](
      l: Array[A], 
      r: Array[A], 
      zero: A,
      plus: (A, A) => A,
      times: (A, A) => A): A = {
    ${dotExpr('l, 'r, 'zero, 'plus, 'times)}
  }

  def dotExpr[A](
      l: Expr[Array[A]], 
      r: Expr[Array[A]],
      zero: Expr[A],
      plus: Expr[(A, A) => A],
      times: Expr[(A, A) => A])(using Quotes, Type[A]): Expr[A] = Log('{
    val lSize = $l.size
    val rSize = $r.size

    if (lSize == rSize) {
      ${ doDotExpr(l, r, zero, plus, times) }
    } else {
      throw new RuntimeException("Arrays must have the same size (got ${lSize} ${rSize})")
    }
  })

  private def doDotExpr[A](
      l: Expr[Array[A]], 
      r: Expr[Array[A]],
      zero: Expr[A],
      plus: Expr[(A, A) => A],
      times: Expr[(A, A) => A])(
      using q: Quotes,
      typeA: Type[A]): Expr[A] = '{
    val left = $l
    val right = $r

    var i = 0
    var sum = $zero

    while (i < left.size) {
      val l = left(i)
      val r = right(i)

      sum = $plus(sum, $times(l, r))

      i += 1
    }

    sum
  }
}

object Dot3 {
  def dotExpr[A](
      l: Expr[Array[A]], 
      r: Expr[Array[A]])(using Quotes, Type[A], StagedNumeric[A]): Expr[A] = Log('{
    val lSize = $l.size
    val rSize = $r.size

    if (lSize == rSize) {
      ${ doDotExpr(l, r) }
    } else {
      throw new RuntimeException("Arrays must have the same size (got ${lSize} ${rSize})")
    }
  })

  private def doDotExpr[A](
      l: Expr[Array[A]], 
      r: Expr[Array[A]])(
      using q: Quotes,
      typeA: Type[A],
      sn: StagedNumeric[A]): Expr[A] = '{
    val left = $l
    val right = $r

    var i = 0
    var sum = ${sn.zero}

    while (i < left.size) {
      val l = left(i)
      val r = right(i)

      sum = ${sn.plus}(sum, ${sn.times}(l, r))

      i += 1
    }

    sum
  }

  abstract class StagedNumeric[A] {
    def zero(using Quotes): Expr[A]
    def plus(using Quotes): Expr[(A, A) => A]
    def times(using Quotes): Expr[(A, A) => A]
  }
}

// Dot3 with friendlier syntax
object Dot4 {
  /*
  def dotExpr[A](
      l: Expr[Array[A]], 
      r: Expr[Array[A]])(using Quotes, Type[A], StagedNumeric[A]): Expr[A] = Log('{
    val lSize = $l.size
    val rSize = $r.size

    if (lSize == rSize) {
      ${ doDotExpr(l, r) }
    } else {
      throw new RuntimeException("Arrays must have the same size (got ${lSize} ${rSize})")
    }
  })
  */

  private def doDotExpr[A](
      l: Expr[Array[A]], 
      r: Expr[Array[A]])(
      using q: Quotes,
      typeA: Type[A],
      sn: StagedNumeric[A]): Expr[A] = '{
    val left = $l
    val right = $r

    var i = 0
    var sum: A = ${sn.zero}

    while (i < left.size) {
      val l: A = left(i)
      val r: A = right(i)

      val lr1: A = ${sn.times}(l, r)
      val lr2: A = l * r
      //sum = ${sn.plus}(sum, l * r)

      i += 1
    }

    sum
  }

  extension [A](l: A)(using sn: StagedNumeric[A]) {
    def *(r: A)(using Quotes, Type[A]): A = '{ 
      ${sn.times}(l, r)
    }

    /*
    def *(r: A)(using Quotes, Type[A]): Expr[A] = '{
      ${sn.times}(l, r)
    }
    */
  }

  abstract class StagedNumeric[A] {
    def zero(using Quotes): Expr[A]
    def plus(using Quotes): Expr[(A, A) => A]
    def times(using Quotes): Expr[(A, A) => A]
  }

}

// Dot4 for any data/accumulator
object Dot5 {
}

/*
 * Dot product using the vector api?
 */
