package remoting.config.demo

import akka.actor.Actor

class RemoteActor extends Actor {

  override def receive: Receive = {
    case msg: String =>
      val senderActor = sender()
      senderActor  ! s"got message $msg from $senderActor at ${self.path}"
  }

}
