package coordinated.transactions

import akka.actor.SupervisorStrategy.{Escalate, Resume, Stop}
import akka.actor.{Actor, ActorLogging, AllForOneStrategy, Props}
import coordinated.transactions.Messages.{AccountBalance, TransferMsg}
import akka.pattern.ask
import akka.transactor.CoordinatedTransactionException
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._


class BankActor extends Actor with ActorLogging {

  implicit val timeout = Timeout(10 seconds)

  val transferActor = context.actorOf(Props[TransferActor], name = "TransferActor")

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
