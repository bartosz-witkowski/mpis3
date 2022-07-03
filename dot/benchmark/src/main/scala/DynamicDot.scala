package mpis3

import jdk.incubator.vector.{IntVector, VectorOperators}

abstract class DynamicDot[A : DynamicDot.Vectorized, B : DynamicDot.Numeric] {
  def dot(l: Array[A], r: Array[A]): B
}

object DynamicDot {
  transparent inline def apply[A, B](
      implicit va: Vectorized[A], 
               nb: Numeric[B], 
               promote: Promote[A, B]): DynamicDot[A, B] = 
    new DynamicDot[A, B] {
      override def dot(left: Array[A], right: Array[A]): B = {
        var i = 0
        var sum: B = nb.zero

        val bound: Int = va.loopBound(left.length)

        while (i < bound) {
          val l: va.Vector = va.vectorFromArray(left, i)
          val r: va.Vector = va.vectorFromArray(right, i)
          
          val vmul = va.mul(l, r)
          sum = sum + promote(va.sum(vmul))

          i = i + va.vectorLength
        }

        while (i < left.size) {
          val l: A = left(i)
          val r: A = right(i)

          sum = sum + promote(l * r) 

          i += 1
        }

        sum
      }
    }

  extension [A](l: A) {
    def *(r: A)(using n: Numeric[A]): A = n.times(l, r)
    def +(r: A)(using n: Numeric[A]): A = n.plus(l, r)
  }

  abstract class Numeric[A] {
    def zero: A
    def plus(l: A, r: A): A
    def times(l: A, r: A): A
  }
  object Numeric {
    given longNumeric: Numeric[Long] =  new Numeric[Long] {
      def zero: Long = 0l
      def plus(l: Long, r: Long) = l + r
      def times(l: Long, r: Long) = l * r
    }
  }

  abstract class Vectorized[A] extends Numeric[A] {
    type Vector

    def vectorLength: Int
    def vectorFromArray(array: Array[A], offset: Int): Vector
    def loopBound(size: Int): Int
    def mul(l: Vector, r: Vector): Vector
    def add(l: Vector, r: Vector): Vector
    def sum(l: Vector): A
  }
  object Vectorized {
    given intVectorized: Vectorized[Int] = new Vectorized[Int] {
      type Vector = IntVector

      def vectorLength: Int = IntVector.SPECIES_PREFERRED.length
      def vectorFromArray(array: Array[Int], offset: Int): Vector = IntVector.fromArray(IntVector.SPECIES_PREFERRED, array ,offset)
      def loopBound(size: Int): Int = IntVector.SPECIES_PREFERRED.loopBound(size)
      def mul(l: Vector, r: Vector): Vector = l.mul(r)
      def add(l: Vector, r: Vector): Vector = l.add(r)
      def sum(l: Vector): Int = l.reduceLanes(VectorOperators.ADD)
      def zero: Int = 0
      def plus(l: Int, r: Int): Int = l + r
      def times(l: Int, r: Int): Int = l * r
    }
  }

  abstract class Promote[A, B] {
    def apply(a: A): B
  }
  object Promote {
    given promoteIntToLong: Promote[Int, Long] = new Promote[Int, Long] {
      def apply(i: Int): Long = i.toLong
    }
  }
}
