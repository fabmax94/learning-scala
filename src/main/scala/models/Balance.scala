enum BalanceType:
  case FOOD, MEAL, CASH

final case class Balance(accountId: String, balanceType: BalanceType, value: Float)
