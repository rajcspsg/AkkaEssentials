package akka
package first 
package app
package mapreduce

import akka.actor.{Actor, ActorRef}
import scala.collection.mutable.ArrayBuffer
import MapReduceMessage._

class MapActor extends Actor {

    val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at", "be", "do", "go", "if", "in", "is", "it", "of", "on", "the", "to")

    val defaultCount: Int = 1

    def receive: Receive = {
        case msg: String => 
        sender ! evaluateExpression(msg)
    }

    def evaluateExpression(line: String): MapData = {
        line.split("""\s+""").foldLeft(MapData(ArrayBuffer.empty[WordCount])) { (index, word) =>
            val currentWord = word.toLowerCase
            if(!STOP_WORDS_LIST.contains(currentWord))
                MapData(index.dataList += WordCount(currentWord, 1))
            else
                index
        }
    }
}