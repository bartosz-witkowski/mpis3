package mpis3

import org.openjdk.jmh.annotations.{Benchmark, Param, Scope, Setup, State}
import org.openjdk.jmh.infra.Blackhole


@State(Scope.Thread)
class BenchmarkTiny {
  /*
   * Takes quite a long time so it's good to variable to split on
   *   i.e run only @Param(Array("30", "32"))
   */
  @Param(Array("3", "8", "30", "32", "100", "128","500", "512", "1000", "1024"))
  var arraySize: Int = 0

  @Param(Array("1", "2", "5", "10", "50", "100"))
  var rightsCount = 0

  var left: Array[Int] = null
  var rhs: Array[Array[Int]] = null

  var precompiledDot: Array[Int] => Long = null

  @Setup
  def setup(): Unit = {
    val random = new scala.util.Random(1234)

    def newRandomArray = {
      val array = new Array[Int](arraySize)
      (0 until arraySize).foreach { i =>
        array(i) = random.nextInt
      }
      array
    }

    left = newRandomArray
    precompiledDot = StagedDot.staged(left)

    rhs = new Array[Array[Int]](rightsCount)
    
    rhs.indices.foreach { r => 
      rhs(r) = newRandomArray
    }
  }

  @Benchmark
  def baseline(blackhole: Blackhole): Unit = {
    val dot = DynamicDot.apply[Int, Long](using DynamicDot.Vectorized.intVectorized, DynamicDot.Numeric.longNumeric, DynamicDot.Promote.promoteIntToLong)

    var i = 0
    while (i < rightsCount) {
      blackhole.consume(dot.dot(left, rhs(i)))
      i += 1
    }
  }

  @Benchmark
  def stagedNoCompile(blackhole: Blackhole): Unit = {
    var i = 0
    while (i < rightsCount) {
      blackhole.consume(precompiledDot(rhs(i)))
      i += 1
    }
  }

  @Benchmark 
  def stagedWithCompilation(blackhole: Blackhole): Unit = {
    val f = StagedDot.staged(left)
    
    var i = 0
    while (i < rightsCount) {
      blackhole.consume(f(rhs(i)))
      i += 1
    }
  }
}
