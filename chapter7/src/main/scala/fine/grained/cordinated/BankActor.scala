package fine.grained.cordinated

import akka.actor.SupervisorStrategy.{Escalate, Resume, Stop}
import akka.actor.{Actor, ActorLogging, AllForOneStrategy, Props}
import akka.pattern.ask
import akka.transactor.CoordinatedTransactionException
import akka.util.Timeout
import coordinated.transactions.Messages.{AccountBalance, TransferMsg}

import scala.concurrent.Await
import scala.concurrent.duration._


class BankActor extends Actor with ActorLogging {

  val transferActor = context.actorOf(Props[TransferActor], name = "TransferActor")
  implicit val timeout = Timeout(10 seconds)

  override def receive: Receive = {
    case transfer: TransferMsg =>
      transferActor ! transfer
    case balance: AccountBalance =>
      val balFuture = (transferActor ? balance).mapTo[AccountBalance]
      val result = Await.result(balFuture, 5 seconds)
      log.info(s"Account #{} , Balance #{} ,", result.acctNumber, result.balance)
  }

  override val supervisorStrategy = AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 10 seconds) {
    case _: CoordinatedTransactionException => Resume
    case _: IllegalStateException => Stop
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

}
