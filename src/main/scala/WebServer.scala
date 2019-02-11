import java.io.File

import akka.Done
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.common.EntityStreamingSupport
import akka.http.scaladsl.model.Multipart.BodyPart
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshaller
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{FileIO, Framing, Sink}
import akka.util.ByteString

import scala.concurrent.Future
import scala.io.StdIn
import scala.concurrent.duration._

object WebServer extends App {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher// needed for the future flatMap/onComplete in the end

  val csvRowConsumerActor: ActorRef =  system.actorOf(CsvRowConsumerActor.props(), "consumer-actor")

//  implicit val csvAsUser = Unmarshaller.strict[ByteString, User] { byteString: ByteString =>
//    val splited = byteString.splitAt(4) // todo more complex
//    User(splited._1.asInstanceOf[Long], splited._2.toString())
//  }

//  implicit val csvStreaming = EntityStreamingSupport.csv()

  val splitLines = Framing.delimiter(ByteString("\r\n"), 256, true)

  val route =

    pathSingleSlash {
      getFromResource("views/index.html")
    } ~
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~
      path("uploadFile") {
        entity(as[Multipart.FormData]) { formData =>

          val done: Future[Done] = formData.parts.mapAsync(1) {
            case b: BodyPart if b.filename.exists(_.endsWith(".csv")) =>
              b.entity.dataBytes
                .via(splitLines)
                .map(_.utf8String.split(",").toVector)
                .runForeach(csv => csvRowConsumerActor ! CsvRowConsumerActor.ConsumeCsvRow(csv))

            case _ => Future.successful(Done)
          }.runWith(Sink.ignore)

          // when processing have finished create a response for the user
          onSuccess(done) { _ =>
            complete {
              "ok!"
            }
          }
        }
      }

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 8078)
//val f : Future[Http.ServerBinding]=Http().bindAndHandle(  getRoute(flightDict, schema, impactCodingMap, modelMap, hist, spark)

  println(s"Server online at http://localhost:8078/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}