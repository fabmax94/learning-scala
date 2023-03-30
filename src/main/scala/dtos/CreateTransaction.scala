package dtos

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class CreateTransaction(sourceAccountId: String, destinationAccountId: String, value: Float, balanceType: String)

object CreateTransaction:
    implicit val decoder: JsonDecoder[CreateTransaction] = DeriveJsonDecoder.gen[CreateTransaction]
    implicit val encoder: JsonEncoder[CreateTransaction] = DeriveJsonEncoder.gen[CreateTransaction]
  
