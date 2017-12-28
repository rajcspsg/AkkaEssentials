package fine.grained.cordinated

object Messages {
  case class AccountBalance(acctNumber: String, balance: Float)
  case class AccountDebit(amount: Float)
  case class AccountCredit(amount: Float)
  case class TransferMsg(amtTobeTransferred: Float)
}
