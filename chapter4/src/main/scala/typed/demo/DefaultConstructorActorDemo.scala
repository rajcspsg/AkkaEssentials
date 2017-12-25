package typed.demo

import akka.actor.{ActorRef, ActorSystem, TypedActor, TypedProps}
import akka.util.Timeout

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object DefaultConstructorActorDemo extends App {

    val system = ActorSystem("TypedActorExample")

    val calculator1:CalculatorInt = TypedActor(system).typedActorOf(TypedProps[Calculator]().withDispatcher("defaultDispatcher"))

    val sumFut: Future[Int] = calculator1.add(1, 3)
    val sumResult = Await.result(sumFut, (5 seconds))

    calculator1.incrementCount()

    val inc: Option[Int] = calculator1.incrementAndReturn()

    println(s"sumResult $sumResult inc $inc")

    val calculatorRef: ActorRef = TypedActor(system).getActorRefFor(calculator1)

    calculatorRef ! "Hi There"

    TypedActor(system).stop(calculator1)

    TypedActor(system).poisonPill(calculator1)
}
