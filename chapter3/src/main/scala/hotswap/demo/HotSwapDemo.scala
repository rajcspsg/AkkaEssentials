package hotswap.demo

import akka.actor.{ActorSystem, Props}
import hotswap.demo.HotSwapMsg.Ping

object HotSwapDemo extends App {
  val system = ActorSystem("HotswapDemo")
  val pingPongActor = system.actorOf(Props[PingPongActor], name = "pingPongActor")
  for (i <- 1 to 20) {
    Thread.sleep(300L)
    pingPongActor ! Ping
  }
}
