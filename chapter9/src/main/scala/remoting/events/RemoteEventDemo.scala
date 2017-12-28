package remoting.events

import akka.actor.{ActorSystem, PoisonPill, Props}
import remoting.config.demo.RemoteActor

object RemoteEventDemo extends App {
  val system = ActorSystem("test")
  val remoteActor = system.actorOf(Props[RemoteActor])
  val remoteActorListener = system.actorOf(Props( new RemoteClientEventListener(remoteActor)), name = "RemoteCllientEventListener")
  system.eventStream.subscribe(remoteActorListener, classOf[RemoteClientEventListener])
  println("Listening...")
  remoteActor ! PoisonPill
}
