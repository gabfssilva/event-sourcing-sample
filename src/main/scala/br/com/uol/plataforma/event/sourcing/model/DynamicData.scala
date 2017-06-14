package br.com.uol.plataforma.event.sourcing.model

import scala.language.dynamics

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
class DynamicData(var data: Map[String, Any] = Map()) extends Dynamic {
  def selectDynamic[T](name: String) = data.get(name).orNull.as[T]

  def updateDynamic(name: String)(value: Any): Unit = data = data + (name -> value)

  def copyTo(dynamicData: DynamicData) = dynamicData.data = data map { x => x }

  implicit class AnyImplicitCast(val any: Any) {
    def as[T] = any.asInstanceOf[T]
  }

  override def toString = s"{${data.map({ case (k, v) => s"$k=$v" }).mkString(",")}}"

  def canEqual(other: Any): Boolean = other.isInstanceOf[DynamicData]

  override def equals(other: Any): Boolean = other match {
    case that: DynamicData =>
      (that canEqual this) && data.filterNot((kv) => that.data.get(kv._1).get.equals(kv._2)).isEmpty
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(data)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}