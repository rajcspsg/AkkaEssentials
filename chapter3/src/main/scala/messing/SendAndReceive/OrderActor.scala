package messing.SendAndReceive

import akka.actor.Actor

class OrderActor extends Actor {

  override def receive: Receive = {
    case msg => sender() ! Order(msg.toString)
  }


}
