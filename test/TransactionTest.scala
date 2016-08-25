import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._


/**
  * Created by SDEVPC on 8/25/2016.
  */
@RunWith(classOf[JUnitRunner])
class TransactionTest extends Specification {

  "TransactionService" should {
    val baseuri = "/transactionservice"
    "send status ok" in new WithApplication {
      (1 to 10).map( num => route(FakeRequest(PUT, s"$baseuri/transact/$num")))
    }

    "send back data" in new WithApplication{
      (1 to 10).map( num => route(FakeRequest(GET, s"$baseuri/transact/$num")))
    }



  }

}
