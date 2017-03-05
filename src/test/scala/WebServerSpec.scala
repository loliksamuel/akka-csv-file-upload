import org.scalatest.FunSpec
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import io.restassured.RestAssured._
import io.restassured.http.ContentType

// you need to start WebServer manually before running this Spec
class WebServerSpec extends FunSpec {

  describe("WebServer") {

    val req = given()
      .port(8080)

    describe("hello endpoint") {
      it("should return hello") {
        val resp = req
          .when()
          .get("/hello")
          .Then()
          .statusCode(200)
          .contentType(ContentType.HTML)
          .extract()
          .body().asString()

        assert(resp contains "Say hello to akka-http")
      }
    }

    describe("csv upload") {

      val csvContent: String =
        """
          |1, ABC
          |2, DEF
          |
        """.stripMargin

      req
        .when()
        .multiPart("input", "someFile.csv", csvContent.getBytes)
        .post("/csv-upload")
        .Then()
        .statusCode(200)
    }

  }


}
