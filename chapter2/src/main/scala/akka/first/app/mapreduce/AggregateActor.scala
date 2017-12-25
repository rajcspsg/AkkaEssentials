package akka.first.app.mapreduce

import akka.actor.Actor
import akka.first.app.mapreduce.MapReduceMessage.{ReduceData, Result}

import scala.collection.mutable.HashMap

class AggregateActor extends Actor {

  val finalReducedMap = new HashMap[String, Int]

  override def receive: Receive = {
    case ReduceData(reduceDataMap) =>
      aggregateInMemoryReduce(reduceDataMap)
    case Result =>
      sender() ! finalReducedMap.toString()
  }

  def aggregateInMemoryReduce(reducedList: Map[String, Int]): Unit = {
    for((key, value) <- reducedList) {
      //val count = finalReducedMap.getOrElse(key, 0) + 1
      println(finalReducedMap)
      if(finalReducedMap contains key)
        finalReducedMap(key) = (value + finalReducedMap.get(key).get)
      else
        finalReducedMap += (key -> value)
    }
  }
}

