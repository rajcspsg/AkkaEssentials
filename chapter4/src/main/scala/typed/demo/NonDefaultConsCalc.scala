package typed.demo

import scala.concurrent.Future

class NonDefaultConsCalc(foo: String) extends CalculatorInt {
  var counter = 0

  import akka.actor.TypedActor.dispatcher

  override def add(first: Int, second: Int): Future[Int] = {
    Future successful(first + second)
  }

  override def incrementCount(): Unit = counter += 1

  override def incrementAndReturn(): Option[Int] = {
    counter += 1
    Some(counter)
  }
}
