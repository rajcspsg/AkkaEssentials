package remoting.config.demo

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object RemoteNodeApplication extends App {

  val system = ActorSystem("RemoteNodeApp", ConfigFactory.load().getConfig("RemoteSys"))
  system.actorOf(Props[RemoteActor], name = "remoteActor")

}
