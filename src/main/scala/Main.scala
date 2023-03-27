import zio._
import zio.http._
import zio.http.model.Method
import services.* 
import models.* 
import repositories.*

object TransactionServer extends ZIOAppDefault {

  def process(): String = 
    val accountSource = createAccount("Fabio")
    val accountDestination = createAccount("Jorge")

    updateBalanceValue(accountSource.id, BalanceType.FOOD, 400)

    val transaction = Transaction(accountSource.id, accountDestination.id, 40, "1")
    processTransaction(transaction)
    
    println(BalanceRepository.findAll())

    "Finish"

  val app: HttpApp[Any, Nothing] = Http.collect[Request] {
    case Method.GET -> !! / "text" => Response.text(TransactionServer.process())
  }

  override val run =
    Server.serve(app).provide(Server.default)
}