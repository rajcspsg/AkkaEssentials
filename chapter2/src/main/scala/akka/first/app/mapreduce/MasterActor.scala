package akka.first.app.mapreduce

import akka.actor.{Actor, Props}
import akka.first.app.mapreduce.MapReduceMessage.{MapData, ReduceData, Result}
import akka.routing.RoundRobinPool

class MasterActor extends Actor {

  val mapActor = context.actorOf(Props[MapActor].withRouter(RoundRobinPool(nrOfInstances = 5)), name = "mapActor")
  val reduceActor = context.actorOf(Props[ReduceActor].withRouter(RoundRobinPool(nrOfInstances = 5)), name = "reduceActor")
  val aggregateActor = context.actorOf(Props[AggregateActor], name = "aggregateActor")

  override def receive: Receive = {
    case line: String =>
      mapActor ! line
    case mapData: MapData =>
      reduceActor ! mapData
    case reduceData: ReduceData =>
      aggregateActor ! reduceData
    case Result =>
      aggregateActor forward Result
  }

}
