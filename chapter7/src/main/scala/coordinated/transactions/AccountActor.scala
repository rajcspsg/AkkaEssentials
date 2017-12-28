package coordinated.transactions

import akka.actor.Actor
import akka.transactor.Coordinated
import coordinated.transactions.Messages.{AccountBalance, AccountCredit, AccountDebit}

import scala.concurrent.stm.Ref

class AccountActor(acctNumber: String, inBalance: Float) extends Actor {

  val balance = Ref(inBalance)

  override def receive: Receive = {
    case value: AccountBalance =>
      sender() ! AccountBalance(acctNumber, balance.single.get)

    case coordinated @ Coordinated(message: AccountDebit) =>
      coordinated atomic { implicit  t =>
        if(balance.get(t) > message.amount)
          balance.transform(_ - message.amount)
        else
          throw new IllegalStateException("Insufficent Balance")
      }

    case coordinated @ Coordinated(message: AccountCredit) =>
      coordinated atomic { implicit  t =>
        balance.transform(_ + message.amount)
      }
  }

}
