package typed.demo

import akka.actor.{ActorRef, ActorSystem, TypedActor, TypedProps}
import akka.routing.{ActorRefRoutee, BroadcastRoutingLogic, Router}
import akka.util.Timeout

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
object RouterDemo extends App {
  val system = ActorSystem("TypedActorExample")

  val calc1: CalculatorInt = TypedActor(system).typedActorOf(TypedProps[Calculator])
  val calc2: CalculatorInt = TypedActor(system).typedActorOf(TypedProps[Calculator])

  val actor1: ActorRef = TypedActor(system).getActorRefFor(calc1)
  val actor2: ActorRef = TypedActor(system).getActorRefFor(calc2)

  val routees = IndexedSeq[ActorRefRoutee](ActorRefRoutee(actor1), ActorRefRoutee(actor2))
  //val router = system.actorOf(new Props().withRouter(BroadcastRouter(routees = routees)))
  val router = Router(
  new BroadcastRoutingLogic(), routees = routees.toIndexedSeq
)
  //router.tell("Hello There!!!")
}
