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
    val sourceBalanceOption = BalanceRepository.findByAccountIdAndBalanceType(transaction.sourceAccountId, transaction.balanceType)
    val destinationBalanceOption = BalanceRepository.findByAccountIdAndBalanceType(transaction.destinationAccountId, transaction.balanceType)
    
    validateTransaction(transaction.value, sourceAccountOption, destinationAccountOption, sourceBalanceOption, destinationBalanceOption)
    
    BalanceRepository.update(new Balance(sourceBalanceOption.get.accountId, 
                                        sourceBalanceOption.get.balanceType, 
                                        sourceBalanceOption.get.value - transaction.value))
    BalanceRepository.update(new Balance(destinationBalanceOption.get.accountId, 
                                        destinationBalanceOption.get.balanceType, 
                                        destinationBalanceOption.get.value + transaction.value))
