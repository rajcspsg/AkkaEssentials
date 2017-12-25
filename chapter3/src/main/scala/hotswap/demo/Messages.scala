package hotswap.demo

sealed trait HotSwapMsg

object HotSwapMsg {
  case object Ping extends  HotSwapMsg
  case object Pong extends  HotSwapMsg
}

