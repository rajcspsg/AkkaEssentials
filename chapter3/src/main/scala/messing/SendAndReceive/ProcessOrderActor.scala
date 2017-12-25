package messing.SendAndReceive

import akka.actor.{Actor, Props}
import akka.util.Timeout
import akka.pattern.pipe
import scala.concurrent.Future
import scala.concurrent.duration._
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global

class ProcessOrderActor extends Actor {

  implicit val  timeout = Timeout(5 seconds)
  val orderActor = context.actorOf(Props[OrderActor])
  val addressActor = context.actorOf(Props[AddressActor])
  val orderAggregatorActor = context.actorOf(Props[OrderAggregatorActor])
  override def receive: Receive = {
    case userId:Integer =>
      val aggResult: Future[OrderHistory] = for {
        order <- (orderActor ? userId).mapTo[Order]
        address <- (addressActor ? userId).mapTo[Address]
      } yield OrderHistory(order, address)
      aggResult pipeTo orderAggregatorActor
  }
}
