package br.com.uol.plataforma.event.sourcing.model

import java.util.Date

import br.com.uol.plataforma.event.sourcing.state.State

abstract class Event[S <: State](val eventDate: Date = new Date()) extends DynamicData {
  override def toString: String = s"${this.getClass.getSimpleName}${super.toString}"

  def applyTo(state: S): S
}