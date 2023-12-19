import Operation.Division

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Matrix(val array: Array[Array[Double]],val vector:Array[Double]){
  def getOperations:List[Seq[Operation]] ={
    val size = this.vector.length

    val divisions: Seq[IndexedSeq[Operation]] = for i <- 0 until size
                                                                                                                               row = for j <- i until size
                          yield Operation.Division(i,j)
    yield row

    val multiplications: Seq[IndexedSeq[IndexedSeq[Operation]]] = for i <- 1 until size
                                                                                                                                                                                   col = for j <- 0 until size
                                subtractRow = for k <- 0 until i
                                  yield Operation.Multiplication(i, j, k)
                                yield subtractRow
    yield col

    val subtractions: Seq[IndexedSeq[IndexedSeq[Operation]]] = for i <- 1 until size
                                                                                                                                                                                col = for j <- 0 until size
                                        subtractRow = for k <- 0 until i
                                          yield Operation.Subtraction(i, j, k)
                              yield subtractRow
    yield col
    List(divisions.flatten,multiplications.flatten.flatten,subtractions.flatten.flatten)
  }
}
object Matrix{
  def apply(size: Int): Matrix = {
    val arr: Array[Array[Double]] = Array.ofDim[Double](size, size)
    val vector: Array[Double] = Array.ofDim[Double](size)
    val rand = new Random()
    for (i <- 0 until size) {
      vector(i) = rand.nextDouble()
      for (j <- 0 until size) {
        arr(i)(j) = rand.nextDouble()
      }
    }
    new Matrix(arr, vector)
  }
  def apply(filename: String): Matrix = {
    val source = scala.io.Source.fromFile(filename)
    val lines = try source.mkString finally source.close()
    val linesArr = lines.split("\n")
    val shape: Int = linesArr(0).trim.toInt
    val arr: Array[Array[Double]] = Array.ofDim[Double](shape, shape)
    println(shape)
    for (i <- 1 until shape + 1) {
      val numbers = linesArr(i).split(" ")
      for (j <- 0 until shape) {
        arr(i - 1)(j) = numbers(j).trim.toDouble
      }
    }
    val vector = linesArr(shape+1).split(" ").map((x:String) => x.trim.toDouble)
    new Matrix(arr, vector)
  }
}
