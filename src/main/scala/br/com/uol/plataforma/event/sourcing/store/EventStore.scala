package br.com.uol.plataforma.event.sourcing.store

import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.state.State

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
trait EventStore[S <: State] {
  def add(aggregateId: String, event: Event[S])
  def get(aggregateId: String): Seq[Event[S]]
}