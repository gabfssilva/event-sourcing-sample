package br.com.uol.plataforma.event.sourcing.store

import br.com.uol.plataforma.event.sourcing.model.Event

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
trait EventStore[T] {
  def add(aggregateId: String, event: Event[T])
  def get(aggregateId: String): Seq[Event[T]]
}