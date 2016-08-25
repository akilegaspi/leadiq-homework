import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._

import scala.util.Random


/**
  * Created by SDEVPC on 8/25/2016.
  */
@RunWith(classOf[JUnitRunner])
class TransactionTest extends Specification {

  "TransactionService" should {
    implicit val app = FakeApplication()
    val baseuri = "/transactionservice"
    "PUT transaction data JSON" in new WithApplication {
      val header = FakeHeaders(List(("type", "application/json")))

      val list = List("cars", "animals", "people")

      def getType = list( new Random().nextInt(list.length - 1))

      def dataGen(num: Long) = {
        val rand = new Random().nextFloat * 1000
          s"""{
              |"amount" : $rand,
              |"types"  : "$getType" ${if (new Random().nextBoolean) s", \"parent_id\": $num "}
              |}""".stripMargin

      }
      (1 to 100).map( num => route(FakeRequest(PUT, s"$baseuri/transact/$num", header, dataGen(num))))
      
    }

  }

}
