package fine.grained.cordinated

import akka.actor.Actor
import akka.transactor.{Coordinated, Transactor}
import coordinated.transactions.Messages.{AccountBalance, AccountCredit, AccountDebit}
import scala.concurrent.stm.Ref._
import scala.concurrent.stm._
import scala.concurrent.stm._
import scala.concurrent.stm.{InTxn, Ref}

class AccountActor(acctNumber: String, inBalance: Float) extends Transactor {

  val balance = Ref(inBalance)

   override def normally: Receive = {
    case _: AccountBalance =>
      sender() ! AccountBalance(acctNumber, balance.single.get)
  }

  override def atomically: InTxn => Receive = {
    case message: AccountDebit =>
        if(balance.single.get < message.amount)
          throw new IllegalStateException("Insufficent Balance")
        else
          balance.transform(_ - message.amount)
    case message: AccountCredit =>
         balance.transform(_ + message.amount)

  }

}
