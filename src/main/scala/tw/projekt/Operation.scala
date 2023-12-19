package tw.projekt

enum Operation(val row1: Int, val column: Int, val row2: Int) {
  case Division(override val row1: Int,override val row2: Int) extends Operation(0,row1,row2)
  case Multiplication(override val row1: Int, override val column: Int, override val row2: Int) extends Operation(column, row1,row2)
  case Subtraction(override val row1: Int, override val column: Int, override val row2: Int) extends Operation(column, row1,row2)
  def isDirectlyDependent(other: Operation): Boolean = (this.ordinal,other.ordinal) match{
      case (0,1) => this.row1 == other.row1 && this.row2 == other.row2
      case (1,2) => this.row1 == other.row1 && this.row2 == other.row2 && this.column == other.column
      case (2,0) => (this.row2 == other.row1 || this.row2 == other.row2 && this.row1 == other.row1 - 1) && this.column == other.row1
      case (2,1) => (this.row2 == other.row1 || (this.row2 == other.row2|| this.row2 == other.row2-1) && this.row1 == other.row1 - 1) && this.column == other.column
      case (2,2) => this.row1 == other.row1-1 && this.row2 == other.row2 && this.column == other.column
      case _ => false
    }
}
