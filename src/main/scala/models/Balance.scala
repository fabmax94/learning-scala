package models

import repositories.AccountRepository

enum BalanceType:
  case FOOD, MEAL, CASH

final case class Balance(accountId: String, balanceType: BalanceType, var value: Float):
  if value < 0 then throw new RuntimeException("Value need greater then 0")

  def getAccount(): Account = AccountRepository.findById(accountId).getOrElse(throw new RuntimeException("Account not found"))

