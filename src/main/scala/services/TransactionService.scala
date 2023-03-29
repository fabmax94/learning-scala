package services

import repositories.*
import models.*
import utils.*

object TransactionService:
    private val CASH_TAX: Float = 0.12

    private def validateTransaction(value: Float, sourceAccountOption: Option[Account], destinationAccountOption: Option[Account],
                sourceBalanceOption: Option[Balance], destinationBalanceOption: Option[Balance]): Unit =
        sourceAccountOption.getOrElse(throw new RuntimeException("Source account not found"))
        destinationAccountOption.getOrElse(throw new RuntimeException("Destination account not found"))
        destinationBalanceOption.getOrElse(throw new RuntimeException("Balance type not found"))
        
        val balance = sourceBalanceOption.getOrElse(throw new RuntimeException("Balance type not found"))
        if balance.value < value then
            throw new RuntimeException("Balance insufficent")


    private def createExecuteTransactionFunction(balanceType: BalanceType): (String, Float) => Unit =
        val tax = balanceType match
            case BalanceType.CASH => CASH_TAX
            case _ => 1
        
        (accountId, value) => AccountService.updateBalanceValue(accountId, balanceType, value + (value * tax))
        
    def processTransaction(transaction: Transaction): Unit = 
        val sourceAccountOption = AccountRepository.findById(transaction.sourceAccountId)
        val destinationAccountOption = AccountRepository.findById(transaction.destinationAccountId)
        val balanceType = transaction.balanceType.convertToBalanceType
        val sourceBalanceOption = BalanceRepository.findByAccountIdAndBalanceType(transaction.sourceAccountId, balanceType)
        val destinationBalanceOption = BalanceRepository.findByAccountIdAndBalanceType(transaction.destinationAccountId, balanceType)
        
        validateTransaction(transaction.value, sourceAccountOption, destinationAccountOption, sourceBalanceOption, destinationBalanceOption)
        
        val executeTransactionFunction = createExecuteTransactionFunction(balanceType)

        executeTransactionFunction(transaction.sourceAccountId, sourceBalanceOption.get.value - transaction.value)
        executeTransactionFunction(transaction.destinationAccountId, destinationBalanceOption.get.value + transaction.value)
