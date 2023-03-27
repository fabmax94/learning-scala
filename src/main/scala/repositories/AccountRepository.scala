package repositories

import models.Account
import scala.collection.mutable.ListBuffer

object AccountRepository:
    private var _accounts: ListBuffer[Account] = ListBuffer()

    def findById(id: String): Option[Account] = _accounts.find(_.id == id)

    def save(account: Account): Unit = _accounts += account