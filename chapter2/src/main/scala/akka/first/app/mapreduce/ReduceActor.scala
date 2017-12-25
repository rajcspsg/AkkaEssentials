package akka.first.app.mapreduce

import akka.actor.Actor
import scala.collection.mutable.ArrayBuffer
import MapReduceMessage._

class ReduceActor extends Actor {

  override def receive: Receive = {
    case MapData(dataList) =>
      sender() ! reduce(dataList)
  }

  def reduce(words: ArrayBuffer[WordCount]): ReduceData = ReduceData {
    words.foldLeft(Map.empty[String, Int]){ (index, words) =>
        if(index contains(words.word))
          index + (words.word -> (index.get(words.word).get + 1))
        else
          index + (words.word -> 1)
    }
  }
}
