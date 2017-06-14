package br.com.uol.plataforma.event.sourcing.model

import java.util.Date

import br.com.uol.plataforma.event.sourcing.state.State

abstract class Event[S <: State] extends DynamicData {
  val eventDate = new Date()

  override def toString: String = s"${this.getClass.getSimpleName}${super.toString}"

  def applyTo(state: S): S

  override def equals(obj: scala.Any): Boolean = super.equals(obj)
}