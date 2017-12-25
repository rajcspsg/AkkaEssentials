package typed.demo

import akka.actor.{ActorSystem, TypedActor, TypedProps}

import scala.concurrent.Await
import scala.concurrent.duration._

object NonDefaultConsTypedActor extends App {

  val system = ActorSystem("NonDefaultConstructor")

  val calculator2: CalculatorInt = TypedActor(system).typedActorOf(TypedProps(classOf[CalculatorInt], new NonDefaultConsCalc("foo")))

  val sumFut = calculator2.add(2, 6)

  val sumResult = Await.result(sumFut, 5 seconds)

  println(s"sumResult $sumResult")

  TypedActor(system).stop(calculator2)
  TypedActor(system).poisonPill(calculator2)
}
