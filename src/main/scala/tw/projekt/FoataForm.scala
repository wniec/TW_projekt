package tw.projekt

import scala.collection.mutable

class FoataForm(val layers: mutable.Buffer[mutable.Buffer[Operation]]) {

}
object FoataForm{
  def apply(dependent: Map[Operation, mutable.Set[Operation]], roots: mutable.Set[Operation], operations: List[Operation]): FoataForm ={
    val layers: mutable.Buffer[mutable.Buffer[Operation]] = mutable.Buffer[mutable.Buffer[Operation]]()
    val queue = new mutable.Queue[(Int, Operation)]()
    val incoming: Map[Operation, Int] = operations.map((o: Operation) =>
      (o,dependent.count((_:Operation, set: mutable.Set[Operation]) => set.contains(o)))).toMap
    val haveToVisit: collection.mutable.Map[Operation, Int] = collection.mutable.Map(incoming.toSeq: _*)
    queue.enqueueAll(roots.map((r: Operation) => (0,r)))
    //println(haveToVisit)
    while(queue.nonEmpty){
      val(layer: Int,operation: Operation) = queue.dequeue()
      if (haveToVisit(operation) == 0){
        if (layers.size <= layer) {
          layers.append(mutable.Buffer[Operation]())
        }
        layers(layer).append(operation)
        if(dependent.contains(operation)) {
          dependent(operation).foreach((o: Operation) => {
            haveToVisit(o) -= 1
            if(haveToVisit(o)==0){
              queue.enqueue((layer + 1, o))
            }
          })
        }
      }
      else{

      }
    }
    new FoataForm(layers)
  }

  def apply(relations: Relations): FoataForm = {
    val dependent = relations.dependent
    val roots = relations.roots
    val operations = relations.operations
    apply(dependent, roots, operations)
  }
}