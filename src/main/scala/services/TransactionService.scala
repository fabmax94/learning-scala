package services

import repositories.*
import models.*
import utils.*

def validateTransaction(value: Float, sourceAccountOption: Option[Account], destinationAccountOption: Option[Account],
            sourceBalanceOption: Option[Balance], destinationBalanceOption: Option[Balance]): Unit =
    sourceAccountOption.getOrElse(throw new RuntimeException("Source account not found"))
    destinationAccountOption.getOrElse(throw new RuntimeException("Destination account not found"))
    destinationBalanceOption.getOrElse(throw new RuntimeException("Balance type not found"))
    
    val balance = sourceBalanceOption.getOrElse(throw new RuntimeException("Balance type not found"))
    if balance.value < value then
        throw new RuntimeException("Balance insufficent")

def processTransaction(transaction: Transaction): Unit = 
    val sourceAccountOption = AccountRepository.findById(transaction.sourceAccountId)
    val destinationAccountOption = AccountRepository.findById(transaction.destinationAccountId)
    val balanceType = transaction.balanceType.convertToBalanceType
    val sourceBalanceOption = BalanceRepository.findByAccountIdAndBalanceType(transaction.sourceAccountId, balanceType)
    val destinationBalanceOption = BalanceRepository.findByAccountIdAndBalanceType(transaction.destinationAccountId, balanceType)
    
    validateTransaction(transaction.value, sourceAccountOption, destinationAccountOption, sourceBalanceOption, destinationBalanceOption)
    
    val sourceBalance = sourceBalanceOption.get
    val destinationBalance = destinationBalanceOption.get
    sourceBalance.value = sourceBalance.value - transaction.value
    destinationBalance.value = destinationBalance.value + transaction.value
    BalanceRepository.update(sourceBalance)
    BalanceRepository.update(destinationBalance)
