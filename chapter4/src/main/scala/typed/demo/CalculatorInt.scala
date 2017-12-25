package typed.demo

import scala.concurrent.Future

trait CalculatorInt {
  def add(first: Int, second: Int): Future[Int]
  def incrementCount(): Unit
  def incrementAndReturn(): Option[Int]
}