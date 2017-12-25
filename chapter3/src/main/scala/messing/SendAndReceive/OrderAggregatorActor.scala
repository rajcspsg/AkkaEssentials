package messing.SendAndReceive

import akka.actor.Actor

class OrderAggregatorActor extends Actor {

  override def receive: Receive = {
    case msg: OrderHistory => println(msg)
  }
}
