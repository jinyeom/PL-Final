package automata

class SystolicArray[T <: SystolicCell](array: List[T]) {
  
  def step {
    array foreach (_.step)
  }
  
  def update {
    array foreach (_.update)
  }
  
  def fullStep {
    step
    update
  }
  
}