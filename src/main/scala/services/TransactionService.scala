package services

import repositories.*
import models.*
import utils.*
import dtos.CreateTransaction

object TransactionService:
  private val CASH_TAX: Float = 0.12

  private val TRANSACTION_STATUS = Map(
    "INSUFFICIENT_BALANCE" -> "Insufficient Balance",
    "INVALID_DATA" -> "Invalid Data",
    "OK" -> "OK"
  )

  private def validateTransaction(
      value: Float,
      sourceBalance: Balance
  ): Option[String] =
    if sourceBalance.value < value then
      Some(TRANSACTION_STATUS("INSUFFICIENT_BALANCE"))
    None

  private def createExecuteTransactionFunction(
      balanceType: BalanceType
  ): (String, Float) => Unit =
    val tax = balanceType match
      case BalanceType.CASH => CASH_TAX
      case _                => 1

    (accountId, value) =>
      AccountService.updateBalanceValue(
        accountId,
        balanceType,
        value + (value * tax)
      )

  private def findDatasToTransaction(
      transaction: CreateTransaction,
      balanceType: BalanceType
  ): Option[(Account, Account, Balance, Balance)] = for
    sourceAccountOption <- AccountRepository.findById(
      transaction.sourceAccountId
    )
    destinationAccountOption <- AccountRepository.findById(
      transaction.destinationAccountId
    )
    sourceBalanceOption <- BalanceRepository.findByAccountIdAndBalanceType(
      transaction.sourceAccountId,
      balanceType
    )
    destinationBalanceOption <- BalanceRepository
      .findByAccountIdAndBalanceType(
        transaction.destinationAccountId,
        balanceType
      )
  yield (
    sourceAccountOption,
    destinationAccountOption,
    sourceBalanceOption,
    destinationBalanceOption
  )

  private def executeProcess(
      value: Float,
      options: Option[(Account, Account, Balance, Balance)],
      balanceType: BalanceType
  ): String = options match
    case None => TRANSACTION_STATUS("INVALID_DATA")
    case Some(data) => {
      val (
        sourceAccount,
        destinationAccount,
        sourceBalance,
        destinationBalance
      ) = data

      val validateOption = validateTransaction(value, sourceBalance)

      validateOption match
        case Some(status) => status
        case None => {
          val executeTransactionFunction = createExecuteTransactionFunction(
            balanceType
          )

          executeTransactionFunction(
            sourceAccount.id,
            sourceBalance.value - value
          )
          executeTransactionFunction(
            destinationAccount.id,
            destinationBalance.value + value
          )

          TRANSACTION_STATUS("OK")
        }
    }

  def process(transaction: CreateTransaction): String =
    val balanceType = transaction.balanceType.convertToBalanceType
    val options = findDatasToTransaction(transaction, balanceType)

    executeProcess(transaction.value, options, balanceType)
