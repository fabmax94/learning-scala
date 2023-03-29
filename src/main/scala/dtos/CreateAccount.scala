package dtos

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class CreateAccount(name: String)

object CreateAccount:
    implicit val decoder: JsonDecoder[CreateAccount] = DeriveJsonDecoder.gen[CreateAccount]
    implicit val encoder: JsonEncoder[CreateAccount] = DeriveJsonEncoder.gen[CreateAccount]
  
