package tw.projekt

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
    val foataForm = FoataForm(relations)
    //println("\n\n\n")
    /*
    foataForm.layers.foreach((layer: mutable.Buffer[Operation]) =>{
      layer.foreach((o: Operation) => print(o," "))
      println("")
    })
    */
    matrix.printArray()
    matrix.gaussianEliminationParallel()
    matrix.printArray()
  }
}
