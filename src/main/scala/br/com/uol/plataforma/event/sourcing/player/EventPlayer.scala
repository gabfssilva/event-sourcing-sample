package br.com.uol.plataforma.event.sourcing.player

import br.com.uol.plataforma.event.sourcing.model.Event

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
object EventPlayer {
  def play[T](initialState: T, aggregateId: String, events: Seq[Event[T]]): T = {
    var state = initialState

    for (event <- events) {
      state = event.applyTo(aggregateId, state)
    }

    state
  }
}