package models

case class Transaction(
    sourceAccountId: String,
    destinationAccountId: String,
    value: Float,
    balanceType: String
)
