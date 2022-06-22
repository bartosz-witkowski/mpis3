package mpis3

object Main {
  def main(args: Array[String]): Unit = {
    println(Dot2.dot[Double](
      Array(1.0, 3.0, -5.0), Array(4.0, -2.0, -1.0),
      0.0,
      _ + _,
      _ * _))
  }
}
