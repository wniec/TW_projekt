private object Main{
  @main
  def main(): Unit = {
    val filepath: String = "src\\main\\scala\\in.txt"
    val matrix = Matrix(filepath)
    val arr = matrix.array
    val vector = matrix.vector
    arr.foreach((x: Array[Double]) => println(x.mkString("Array(", ", ", ")")))
    println(vector.mkString("Array(", ", ", ")"))
    val operations = matrix.getOperations
    for(i <- 0 until 3)
      println(operations(i))
  }
}
