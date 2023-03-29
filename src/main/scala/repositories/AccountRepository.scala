package repositories

import models.Account

object AccountRepository:
    private var _accounts: List[Account] = List()

    def findById(id: String): Option[Account] = _accounts.find(_.id == id)

    def save(account: Account): Unit = _accounts = account :: _accounts

    def findAll(): List[Account] = _accounts