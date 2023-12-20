package tw.projekt

import breeze.linalg.DenseMatrix

import scala.collection.mutable
private object Main{
  def main(args: Array[String]): Unit = {
    val filepath: String = "src\\main\\scala\\in.txt"
    val matrix = Matrix(filepath)
    val relations = Relations(matrix)
    //for(i <- relations.dependent)
      //println(i)
      /*
      relations.dependent.keys.foreach((o:Operation) =>{
        print(o.toString)
        print("\t\t")
        relations.dependent(o).foreach((o1:Operation) => print(o1.toString," "))
        println("")
      })
      */
    matrix.printArray()
    matrix.gaussianEliminationParallel()
    matrix.printArray()
    val size = matrix.array.length
    val indices = IndexedSeq(0,size)
    val sliced: Array[Array[Double]] = matrix.array.map((row: Array[Double]) => row.slice(0,size))//
    matrix.backSubstitution()
    matrix.printArray()
  }
}
