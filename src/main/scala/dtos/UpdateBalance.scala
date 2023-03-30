package dtos

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class UpdateBalance(value: Float)

object UpdateBalance:
  implicit val decoder: JsonDecoder[UpdateBalance] =
    DeriveJsonDecoder.gen[UpdateBalance]
  implicit val encoder: JsonEncoder[UpdateBalance] =
    DeriveJsonEncoder.gen[UpdateBalance]
