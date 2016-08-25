package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json._

import scala.collection.mutable.{Map => Mmap}
import lib.Transaction
import play.api.Logger
/**
  * Created by SDEVPC on 8/24/2016.
  */
class TransactionController extends Controller {

  import Transaction._

  val data = Mmap.empty[Long, Transaction]

  def insert(id: Long)= Action { req =>
    val json = req.body.asJson
    Logger.info(json.toString)
    json.map{ body =>
      val transact = body.as[Transaction]
      transact.parentId match {
        case Some(parentId) =>
          data.get(parentId).fold(Ok(Json.obj("status" -> "not ok"))){ _ =>
            data.update(id, transact)
            Ok(Json.obj("status" -> "ok"))
          }
        case None =>
          data.update(id,transact)
          Ok(Json.obj("status" -> "ok"))
      }
    }.getOrElse(Ok(Json.obj("status" -> "not ok")))
  }

  def retrieve(id: Long)= Action { req =>
    data.get(id).fold(Ok(Json.obj("message" -> "no data to retrieve")))( data => Ok(Json.toJson(data)))
  }

  def sum(id: Long) = Action {

    data.get(id).fold(Ok(Json.obj("message" -> "no data to sum")))( d => {
      Ok(Json.obj("sum" -> d.parentId.fold(d.amount)( pDataId => data(pDataId).amount + d.amount)))
    })

  }

  def types(t: String) = Action {
    Ok(Json.arr(data.values.toList.find( data => data.dataType == t)))
  }

}
