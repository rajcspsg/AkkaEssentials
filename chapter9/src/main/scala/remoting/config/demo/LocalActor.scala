package remoting.config.demo

import akka.actor.{Actor, ActorLogging}
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.Await
import scala.concurrent.duration._

class LocalActor extends Actor with ActorLogging {

  val remoteActor = context.actorSelection("akka.tcp://RemoteNodeApp@localhost:5052/user/remoteActor")

  implicit val timeout = Timeout(5 seconds)

  override def receive: Receive = {
    case msg: String =>
      val futMsg  = (remoteActor ? msg).mapTo[String]
      val result = Await.result(futMsg, timeout.duration)
      log.info(s"message from Server -> {} ", result)
  }

}
