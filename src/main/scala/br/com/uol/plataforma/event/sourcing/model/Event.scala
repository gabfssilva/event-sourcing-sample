package br.com.uol.plataforma.event.sourcing.model

import java.util.Date

abstract class Event[T] extends DynamicData {
  val eventDate = new Date()

  override def toString: String = s"${this.getClass.getSimpleName}${super.toString}"

  def applyTo(aggregateId: String, t: T): T

  override def equals(obj: scala.Any): Boolean = super.equals(obj)
}