package tw.projekt

import tw.projekt.Operation.Division

import java.util
import java.util.concurrent.CountDownLatch
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Matrix(val array: Array[Array[Double]]){
  val size: Int = this.array.length
  private val foataForm = FoataForm(Relations(this))
  private val multipliers: mutable.Buffer[Double] = Array.ofDim[Double](size).toBuffer
  private var done = new CountDownLatch(0)
  def printArray(): Unit ={
    array.foreach((arr: Array[Double]) => {
      arr.foreach((d: Double) => print("%.2f\t".format(d)))
      println("")
    })
    println("\n\n\n")
  }
  def gaussianEliminationSequential(): Unit =foataForm.layers.foreach((layer: mutable.Buffer[Operation]) =>
    layer.foreach((operation: Operation) => perform(operation)))
  def getThreads: mutable.Seq[mutable.Buffer[Thread]] =foataForm.layers.map((layer: mutable.Buffer[Operation]) =>
    layer.map((operation: Operation) =>
    new Thread{
      override def run(): Unit ={
        //println("performing an operation")
        perform(operation)
        done.countDown()
    }}))
  def gaussianEliminationParallel(): Unit ={
    val threads = getThreads
    threads.foreach((layer: mutable.Buffer[Thread]) => {
      done = new CountDownLatch(layer.size)
      layer.foreach((thread: Thread) => {
        thread.start()
      })
      done.await()
    })
  }
  def backSubstitution(): Unit ={
    for (i <- size-1 to (0,-1)){
      val x = array(i)(i)
      for (j <- i - 1 to(0, -1)) {
        val divider = array(j)(i) / x
        for (k <- j to size) {
          array(j)(k) -= (divider * array(i)(k))
        }
      }
      array(i)(i) /= x
      array(i)(size) /= x
    }
  }
  def solve(): Unit={
    gaussianEliminationParallel()
    backSubstitution()
  }
  def lhs: Array[Array[Double]] = array.map((row: Array[Double]) => row.slice(0,size))
  def rhs: Array[Double] = array.map((row: Array[Double]) => row(size))
  def perform(operation:Operation): Unit ={
    //println("operation performed")
    val (i,j,k) = (operation.row1,operation.column, operation.row2)
    operation.ordinal match{
      case 0 => multipliers(k) = array(i)(i)/array(k)(i)
      case 1 => array(k)(j) *= multipliers(k)
      case 2 => array(k)(j) -= array(i)(j)
    }
    //this.printArray()
  }
  def getDivisions: Seq[IndexedSeq[Operation]] =
    for i <- 1 until size
        row = for j <- 0 until i
          yield Operation.Division(j, i)
    yield row
  def getMultiplications: Seq[IndexedSeq[Operation]] =
    for i <- 0 until size
        col = for j <- 0 until i
                  subtractRow = for k <- j until size + 1
                    yield Operation.Multiplication(j, k, i)
        yield subtractRow
    yield col.flatten
  def getSubtractions: Seq[IndexedSeq[Operation]] =
    for i <- 0 until size
        col = for j <- 0 until size + 1
                  subtractRow = for k <- 0 until math.min(i, j + 1)
                    yield Operation.Subtraction(k, j, i)
        yield subtractRow
    yield col.flatten
  def getOperations:List[Seq[Operation]] = List(getDivisions.flatten,getMultiplications.flatten,getSubtractions.flatten)
}
object Matrix{
  def apply(size: Int): Matrix = {
    val arr: Array[Array[Double]] = Array.ofDim[Double](size, size+1)
    val vector: Array[Double] = Array.ofDim[Double](size)
    val rand = new Random()
    for (i <- 0 until size) {
      vector(i) = rand.nextDouble()
      for (j <- 0 until size) {
        arr(i)(j) = rand.nextDouble()
      }
      arr(i)(size) = vector(i)
    }
    new Matrix(arr)
  }
  def apply(filename: String): Matrix = {
    val source = scala.io.Source.fromFile(filename)
    val lines = try source.mkString finally source.close()
    val linesArr = lines.split("\n")
    val shape: Int = linesArr(0).trim.toInt
    val arr: Array[Array[Double]] = Array.ofDim[Double](shape, shape + 1)
    val vector = linesArr(shape+1).split(" ").map((x:String) => x.trim.toDouble)
    for (i <- 1 until shape + 1) {
      val numbers = linesArr(i).split(" ")
      for (j <- 0 until shape) {
        arr(i - 1)(j) = numbers(j).trim.toDouble
      }
      arr(i-1)(shape) = vector(i-1)
    }
    new Matrix(arr)
  }

  def compare(a: Double, b: Double, epsilon: Double): Boolean = Math.abs(a - b) < epsilon
}
