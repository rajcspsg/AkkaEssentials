package messing.SendAndReceive

sealed  trait SendReceive
case class Order(msg: String) extends SendReceive
case class Address(msg: String) extends  SendReceive
case class OrderHistory(order: Order, address: Address) extends SendReceive
