package services

import models.*
import dtos.CreateAccount
import repositories.*
import java.util.UUID
import dtos.GetAccount

object AccountService:
  private val INITIAL_BALANCE = 0

  def createAccount(createAccount: CreateAccount): Account =
    val account = new Account(UUID.randomUUID.toString(), createAccount.name)
    AccountRepository.save(account)
    generateBalances(account).map(BalanceRepository.save(_))
    account

  def findAll(): List[GetAccount] = AccountRepository
    .findAll()
    .map(account =>
      GetAccount(
        account.id,
        account.name,
        BalanceRepository.findByAccountId(account.id)
      )
    )

  def updateBalanceValue(
      accountId: String,
      balanceType: BalanceType,
      value: Float
  ): Unit =
    val balance = BalanceRepository
      .findByAccountIdAndBalanceType(accountId, balanceType)
      .get
    BalanceRepository.update(balance.updateValue(value))

  private def generateBalances(account: Account): Array[Balance] =
    BalanceType.values.map(Balance(account.id, _, INITIAL_BALANCE))
