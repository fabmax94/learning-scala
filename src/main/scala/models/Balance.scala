package models

import repositories.AccountRepository
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}
import zio.json.internal.Write

enum BalanceType:
  case FOOD, MEAL, CASH

case class Balance(accountId: String, balanceType: BalanceType, var value: Float):
  if value < 0 then throw new RuntimeException("Value need greater then 0")

  def getAccount(): Account = AccountRepository.findById(accountId).getOrElse(throw new RuntimeException("Account not found"))

  def updateValue(newValue: Float): Balance =
    this.copy(value = newValue)

object Balance:
    implicit val decoder: JsonDecoder[Balance] = DeriveJsonDecoder.gen[Balance]
    implicit val encoder: JsonEncoder[Balance] = DeriveJsonEncoder.gen[Balance]

object BalanceType:
    implicit val decoder: JsonDecoder[BalanceType] = DeriveJsonDecoder.gen[BalanceType]
    implicit val encoder: JsonEncoder[BalanceType] = JsonEncoder[String].contramap(balanceType => balanceType.toString())