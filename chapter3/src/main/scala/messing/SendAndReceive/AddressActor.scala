package messing.SendAndReceive

import akka.actor.Actor

class AddressActor extends Actor {

  override def receive: Receive = {
    case msg =>sender() ! Address(msg.toString)
  }
}
