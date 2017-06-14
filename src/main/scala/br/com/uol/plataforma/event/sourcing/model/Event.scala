package br.com.uol.plataforma.event.sourcing.model

import java.util.Date

abstract class Event[S] extends DynamicData {
  val eventDate = new Date()

  override def toString: String = s"${this.getClass.getSimpleName}${super.toString}"

  def applyTo(aggregateId: String, state: S): S

  override def equals(obj: scala.Any): Boolean = super.equals(obj)
}