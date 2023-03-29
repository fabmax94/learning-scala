import sttp.tapir.PublicEndpoint
import sttp.tapir.ztapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zio.http.HttpApp
import zio.http.{Server, ServerConfig}
import zio._
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}
import services.* 
import models.* 
import repositories.*
import dtos.*
import utils.*


object LearningScalaHttpServer extends ZIOAppDefault {

  val vaccinationDetails = jsonBody[GetAccount]

  val accountsEndpoint: PublicEndpoint[Unit, Unit, List[GetAccount], Any] =
    endpoint.get
      .in("accounts")
      .out(jsonBody[List[GetAccount]])
  
  val transactionProcessEndpoint: PublicEndpoint[Unit, Unit, String, Any] =
    endpoint
      .in("transactions")
      .in("process")
      .out(stringBody)
      .get

  val accountsCreateEndpoint: PublicEndpoint[CreateAccount, Unit, Account, Any] =
    endpoint
      .in("accounts")
      .in(jsonBody[CreateAccount])
      .out(jsonBody[Account])
      .post
  
  val balanceUpdateEndpoint: PublicEndpoint[(String, String, UpdateBalance), Unit, Unit, Any] =
    endpoint
      .in("accounts")
      .in(path[String]("accountId"))
      .in(path[String]("balanceType"))
      .in(jsonBody[UpdateBalance])
      .put
  
  val app: HttpApp[Any, Throwable] =
    ZioHttpInterpreter().toHttp(accountsEndpoint.zServerLogic(name => ZIO.succeed(AccountService.findAll()))) ++
    ZioHttpInterpreter().toHttp(transactionProcessEndpoint.zServerLogic(name => ZIO.succeed(process()))) ++
    ZioHttpInterpreter().toHttp(accountsCreateEndpoint.zServerLogic(account => ZIO.succeed(AccountService.createAccount(account)))) ++ 
    ZioHttpInterpreter().toHttp(balanceUpdateEndpoint.zServerLogic((accountId, balanceType, updateBalance) => ZIO.succeed(AccountService.updateBalanceValue(accountId, balanceType.convertToBalanceType, updateBalance.value))))

  override def run =
    Server
      .serve(app.withDefaultErrorResponse)
      .provide(
        ServerConfig.live(ServerConfig.default.port(8090)),
        Server.live
      )
      .exitCode
}