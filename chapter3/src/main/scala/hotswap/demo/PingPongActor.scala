package hotswap.demo

import akka.actor.Actor
import hotswap.demo.HotSwapMsg.{Ping, Pong}


class PingPongActor extends Actor {

  var count = 0
  import context._

  override def receive: Receive = receivePing
   def receivePing: Receive = {
    case ping => {
      println("PING")
      count = count + 1
      Thread.sleep(1000)
      self ! Pong
      become (
        receivePong
        )
      if(count > 10) context.stop(self)
    }
  }

  def receivePong: Receive = {
    case Pong =>
      println("PONG")
      count += 1
      Thread.sleep(1000)
      self ! Ping
      become(receivePing)

  }
}
