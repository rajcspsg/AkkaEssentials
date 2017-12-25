package akka.first.app.mapreduce

import akka.actor.{ActorSystem, Props}
import akka.first.app.mapreduce.MapReduceMessage.Result
import akka.util.Timeout

import scala.concurrent.duration._
import akka.pattern.ask

import scala.concurrent.Await

object MapReduceApp extends App {
  val system = ActorSystem("MapReduceApp")
  val master = system.actorOf(Props[MasterActor], name = "master")
  implicit val timeout = Timeout(5 seconds)

  master ! "The quick brown fox tried to jump over the lazy dog and fell"
  master ! "Dog's man best friend"
  master ! "Dog and fox belong to te same family"


  Thread.sleep(2000)
  val future = (master ? Result).mapTo[String]

  val result = Await.result(future, timeout.duration)



  println("result \t" + result)

  // system.
  // ActorSystem.sh
}
