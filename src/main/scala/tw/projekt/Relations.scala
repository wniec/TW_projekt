package tw.projekt

import scala.collection.mutable
import scala.collection.mutable.Set

class Relations(val operations: List[Operation], val dependent: Map[Operation,mutable.Set[Operation]], val roots: mutable.Set[Operation]) {

}
object Relations{
  def getMinimalDependent(operation: Operation, dependent: Map[Operation, mutable.Set[Operation]]):mutable.Set[Operation]={
    if(dependent.contains(operation)){
      val newDependent: mutable.Set[Operation] = new mutable.LinkedHashSet[Operation]
      dependent(operation).foreach((o: Operation) => newDependent.addAll(getMinimalDependent(o, dependent)))
      val result = newDependent.union(dependent(operation))
      dependent(operation).subtractAll(newDependent)
      result
    }
    else
      mutable.Set.empty
  }
  def apply(matrix: Matrix): Relations ={
    val operations: List[Operation] = matrix.getOperations.flatten
    val dependent: Map[Operation, mutable.Set[Operation]] = operations.flatMap((o: Operation) =>
      operations.filter((op: Operation) => o.isDirectlyDependent(op)).map((op1: Operation) =>
        (o,op1))).groupBy(_._1).map(v => (v._1, v._2.map(_._2).to(collection.mutable.LinkedHashSet)))
    val roots = operations.to(collection.mutable.LinkedHashSet)
    dependent.values.foreach((set: mutable.Set[Operation]) => roots.subtractAll(set))
    //println(roots)
    //roots.foreach((o: Operation) => getMinimalDependent(o,dependent))
    new Relations(operations, dependent, roots)
  }
}
