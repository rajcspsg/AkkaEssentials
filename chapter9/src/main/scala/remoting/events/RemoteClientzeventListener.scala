package remoting.events

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.remote.{DisassociatedEvent, RemotingErrorEvent, RemotingListenEvent, RemotingShutdownEvent}

class RemoteClientEventListener (jobScheduler: ActorRef) extends  Actor with ActorLogging {

  override def receive: Receive = {
    case event: RemotingErrorEvent => println()
    case RemotingShutdownEvent => println()
    case event: RemotingListenEvent => println()
    case event: DisassociatedEvent => println()
    case event => println()
  }

}
