package repositories

import models.*

object BalanceRepository extends Repository[Balance]:

  override def equal(entityA: Any, entityB: Any): Boolean =
    val entityAInstance = entityA.asInstanceOf[(String, BalanceType)]
    val entityBInstance = entityB.asInstanceOf[Balance]
    entityAInstance(0) == entityBInstance.accountId && entityAInstance(
      1
    ) == entityBInstance.balanceType

  def findByAccountIdAndBalanceType(
      accountId: String,
      balanceType: BalanceType
  ): Option[Balance] =
    val balance =
      entities.find(balance => equal(balance, (accountId, balanceType)))

    balance match
      case Some(value) => balance
      case _ =>
        entities.find(balance =>
          balance.accountId == accountId && balance.balanceType == BalanceType.CASH
        )

  def findAll(): List[Balance] = entities

  def findByAccountId(accountId: String): List[Balance] =
    entities.filter(_.accountId == accountId)
