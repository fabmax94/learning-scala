@main def process: Unit = 
  val accountSource = createAccount("Fabio")
  val accountDestination = createAccount("Jorge")

  updateBalanceValue(accountSource.id, BalanceType.FOOD, 400)

  val transaction = Transaction(accountSource.id, accountDestination.id, 40, BalanceType.FOOD)
  processTransaction(transaction)
  
  println(BalanceRepository.findAll())

