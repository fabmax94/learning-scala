package dtos

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}
import models.Balance

case class GetAccount(id: String, name: String, balances: List[Balance])

object GetAccount:
  implicit val decoder: JsonDecoder[GetAccount] =
    DeriveJsonDecoder.gen[GetAccount]
  implicit val encoder: JsonEncoder[GetAccount] =
    DeriveJsonEncoder.gen[GetAccount]
