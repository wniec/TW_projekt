enum Operation() {
  case Division(row1: Int, row2: Int) extends Operation
  case Multiplication(column: Int, row: Int, subtractRow: Int) extends Operation
  case Subtraction(column: Int, row1: Int, row2: Int) extends Operation
}
