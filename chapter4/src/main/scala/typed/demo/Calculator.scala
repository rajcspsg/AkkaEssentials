package typed.demo

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{ActorRef, OneForOneStrategy, SupervisorStrategy, TypedActor}
import akka.actor.TypedActor.{PostStop, PreStart, Receiver, Supervisor}
import akka.event.Logging

import scala.concurrent.duration._
import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global

class Calculator extends CalculatorInt with PreStart with PostStop with Supervisor with Receiver {

  var counter = 0

  import akka.actor.TypedActor.dispatcher
  import akka.actor.TypedActor.context

  val log = Logging(context.system, TypedActor.self.getClass)

  override def supervisorStrategy(): SupervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 5, withinTimeRange = 10 seconds) {
      case _: ArithmeticException => Resume
      case _: IllegalArgumentException => Restart
      case _: NullPointerException => Stop
      case _: Exception => Escalate
    }
  override def preStart(): Unit = {
    log.info("Calculator Actor Started")
  }

  override def postStop(): Unit = {
    log.info("Calculator Actor Stopped")
  }

   override def onReceive(msg: Any, sender: ActorRef): Unit = {
    log.info(s"Message received, ${msg}msg")
   }


  override def add(first: Int, second: Int): Future[Int] = {
    Future successful(first + second)
  }

  override def incrementCount(): Unit = counter += 1

  override def incrementAndReturn(): Option[Int] = {
    counter += 1
    Some(counter)
  }
}
