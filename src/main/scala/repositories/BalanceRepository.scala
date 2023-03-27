package repositories

import scala.collection.mutable.ListBuffer
import models.*

object BalanceRepository:
    private var _balances: ListBuffer[Balance] = ListBuffer()

    def findByAccountIdAndBalanceType(accountId: String, balanceType: BalanceType): Option[Balance] =
        val balance = _balances.find(balance => balance.accountId == accountId && balance.balanceType == balanceType)

        if !balance.isEmpty then
            return balance
        _balances.find(balance => balance.accountId == accountId && balance.balanceType == BalanceType.CASH)
        
    def save(balance: Balance): Unit = _balances += balance
    
    def update(balanceToUpdate: Balance): Unit =
        _balances = _balances.filter(balance => !(balance.accountId == balanceToUpdate.accountId && 
                                        balance.balanceType == balanceToUpdate.balanceType))
        save(balanceToUpdate)

    def findAll(): ListBuffer[Balance] = _balances