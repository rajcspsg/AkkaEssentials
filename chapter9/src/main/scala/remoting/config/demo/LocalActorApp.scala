package remoting.config.demo

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object LocalActorApp extends App {
  val config = ConfigFactory.load().getConfig("LocalSys")

  val system = ActorSystem("LocalNodeApp", config)

  val clientActor = system.actorOf(Props[LocalActor])

  clientActor ! "Hello"
  Thread.sleep(4000L)
  //system.scheduler.
}
