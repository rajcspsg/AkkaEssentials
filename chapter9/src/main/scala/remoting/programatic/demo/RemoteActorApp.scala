package remoting.programatic.demo

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import remoting.config.demo.RemoteActor

object RemoteActorApp extends App {

  val system = ActorSystem("RemoteNodeApp", ConfigFactory.load().getConfig("RemoteProgrammatically"))
  val remoteActor = system.actorOf(Props[RemoteActor], name = "remoteActorAddr")

  remoteActor ! "Hello!"

  val actorSelection = system.actorSelection("akka.tcp://RemoteNodeApp@localhost:2553/user/remoteActorAddr")
  Thread.sleep(4000L)

  actorSelection ! "Hello!"

}
