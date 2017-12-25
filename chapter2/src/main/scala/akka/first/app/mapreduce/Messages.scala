package akka
package first 
package app
package mapreduce

import scala.collection.mutable.ArrayBuffer

sealed trait MapReduceMessage

object MapReduceMessage {
  case class WordCount(word: String, count: Int) extends MapReduceMessage
  case class MapData(dataList: ArrayBuffer[WordCount]) extends MapReduceMessage
  case class ReduceData(reduceDataMap: Map[String, Int]) extends  MapReduceMessage
  case object Result extends MapReduceMessage
}


