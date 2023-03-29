package models

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Account(id: String, name: String)

object Account:
    implicit val decoder: JsonDecoder[Account] = DeriveJsonDecoder.gen[Account]
    implicit val encoder: JsonEncoder[Account] = DeriveJsonEncoder.gen[Account]
  
