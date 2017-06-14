package br.com.uol.plataforma.event.sourcing.player

import br.com.uol.plataforma.event.sourcing.model.Event

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
object EventPlayer {
  def play[S](initialState: S, aggregateId: String, events: Seq[Event[S]]): S =
    events.foldLeft(initialState)((s, e) => e applyTo(aggregateId, s))
}