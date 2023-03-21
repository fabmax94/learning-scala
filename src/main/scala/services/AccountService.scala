import java.util.UUID

def createAccount(name: String): Account =
    val account = new Account(UUID.randomUUID.toString(), name)
    AccountRepository.save(account)
    generateBalances(account).map(BalanceRepository.save(_))
    account


def updateBalanceValue(accountId: String, balanceType: BalanceType, value: Float): Unit =
    BalanceRepository.update(new Balance(accountId, balanceType, value))


def generateBalances(account: Account): Array[Balance] =
    BalanceType.values.map(Balance(account.id, _, 0))