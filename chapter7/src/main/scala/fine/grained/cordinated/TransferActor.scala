package fine.grained.cordinated

import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, AllForOneStrategy, Props}
import akka.transactor.{Coordinated, CoordinatedTransactionException}
import akka.util.Timeout
import coordinated.transactions.Messages.{AccountCredit, AccountDebit, TransferMsg}

import scala.concurrent.duration._

class TransferActor extends Actor {

  implicit val timeout = Timeout(10 seconds)

  val fromAccount = "XYZ"
  val toAccount = "ABC"

  val from = context.actorOf(Props(new AccountActor(fromAccount, 5000)), name = fromAccount)
  val to = context.actorOf(Props(new AccountActor(toAccount, 1000)), name = toAccount)

  override val supervisorStrategy = AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 10 seconds) {
    case _: CoordinatedTransactionException => Resume
    case _: IllegalStateException => Resume
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

  override def receive: Receive = {
    case message: TransferMsg =>
      val coordinated = Coordinated()

      coordinated atomic { implicit  t =>
        to ! coordinated(new AccountCredit(message.amtTobeTransferred))
        from ! coordinated(new AccountDebit(message.amtTobeTransferred))
      }
  }
}
