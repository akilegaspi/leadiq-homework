package lib

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

/**
  * Created by SDEVPC on 8/25/2016.
  */
object Transaction {


  case class Transaction(amount: Double, dataType: String, parentId: Option[Long])
  implicit val transactionWrites : Writes[Transaction] = (
    (JsPath \ "amount").write[Double] and
      (JsPath \ "type").write[String] and
      (JsPath \ "parent_id").writeNullable[Long]
    )(unlift(Transaction.unapply))

  implicit val transactionReads : Reads[Transaction] = (
    (JsPath \ "amount").read[Double] and
      (JsPath \ "type").read[String] and
      (JsPath \ "parent_id").readNullable[Long]
    )(Transaction.apply _)

}
