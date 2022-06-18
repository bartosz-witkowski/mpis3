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

/*
 * Dot product for a generic type A using typeclasses.
 */
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

/*
 * Dot3 with friendlier syntax
 */
object Dot4 {
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
      using qctx: Quotes,
      typeA: Type[A],
      sn: StagedNumeric[A]): Expr[A] = {

    '{
      val left = $l
      val right = $r

      var i = 0
      var sum: A = ${sn.zero}

      while (i < left.size) {
        val l: A = left(i)
        val r: A = right(i)

        sum = ${ 'sum + ('l * 'r) }

        i += 1
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
}

/* 
 * Dot4 for any data/accumulator
 */
object Dot5 {
  def dotExpr[A, B](
      l: Expr[Array[A]], 
      r: Expr[Array[A]])(using Quotes, Type[A], Type[B], StagedNumeric[A], StagedNumeric[B], StagedPromote[A, B]): Expr[B] = Log('{
    val lSize = $l.size
    val rSize = $r.size

    if (lSize == rSize) {
      ${ doDotExpr(l, r) }
    } else {
      throw new RuntimeException("Arrays must have the same size (got ${lSize} ${rSize})")
    }
  })

  private def doDotExpr[A, B](
      l: Expr[Array[A]], 
      r: Expr[Array[A]])(
      using qctx: Quotes,
      typeA: Type[A],
      typeB: Type[B],
      na: StagedNumeric[A],
      nb: StagedNumeric[B],
      promote: StagedPromote[A, B]): Expr[B] = {

    '{
      val left = $l
      val right = $r

      var i = 0
      var sum: B = ${nb.zero}

      while (i < left.size) {
        val l: A = left(i)
        val r: A = right(i)

        sum = ${ 'sum + promote('l * 'r) }

        i += 1
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

  abstract class StagedPromote[A, B] {
    def apply(using Quotes): Expr[A] => Expr[B]
  }
}

import scala.reflect.ClassTag

private[mpis3] given arrayFromExpr[T](using Type[T], FromExpr[T], ToExpr[Array[T]], ClassTag[T]): FromExpr[Array[T]] = new FromExpr[Array[T]] {
  def unapply(x: Expr[Array[T]])(using quotes: Quotes) = {
    import quotes.reflect.*

    def get(x: Term): Option[Array[T]] = x match {
      case Inlined(
          Some(TypeIdent("ToExpr$")), 
          Nil, 
          Apply(Select(Ident("Array"), "apply"), list)) => 
        list match {
          case Nil =>
            Some(Array.empty[T])
          case x :: xs =>
            x.asExprOf[T].value.flatMap { head =>
              xs match {
                case List(Typed(Inlined(_, _, xs), _)) => 
                  xs.asExprOf[Seq[T]].value.map { tail =>
                    (head +: tail).toArray
                  }
                case other =>
                  None
              }
           }
        }
    
      case other => 
        None
    }

    get(x.asTerm)
  }
}

/*
 * Dot5 using the vector api
 */
object Dot6 {
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

    if (_left.size % vectorLength == 0) '{
      val left: Array[A] = ${Expr(_left)}
      val right: Array[A] = ${Expr(_right)}
      var i = 0
      var sum: B = ${nb.zero}

      val bound: Int = ${na.loopBound(Expr(_left.length))}

      while (i < bound) {
        val l: na.Vector = ${na.vectorFromArray('left)('i)}
        val r: na.Vector = ${na.vectorFromArray('right)('i)}
        
        val vmul = ${na.mul('l)('r)}
        sum = ${ 'sum + promote(na.sum('vmul)) }

        i += ${ na.vectorLength }
      }

      sum
    } else '{
      val left: Array[A] = ${Expr(_left)}
      val right: Array[A] = ${Expr(_right)}

      var i = 0
      var sum: B = ${nb.zero}

      val bound: Int = ${na.loopBound('{left.length})}

      while (i < bound) {
        val l: na.Vector = ${na.vectorFromArray('left)('i)}
        val r: na.Vector = ${na.vectorFromArray('right)('i)}
        
        val vmul = ${na.mul('l)('r)}
        sum = ${ 'sum + promote(na.sum('vmul)) }

        i += ${ na.vectorLength }
      }

      while (i < ${Expr(_left.size)}) {
        val l: A = left(i)
        val r: A = right(i)

        sum = ${ 'sum + promote('l * 'r) }

        i += 1
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

// Dot7 - Dot6 without code duplication
