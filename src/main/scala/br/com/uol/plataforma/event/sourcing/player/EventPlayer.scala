package br.com.uol.plataforma.event.sourcing.player

import br.com.uol.plataforma.event.sourcing.commands.Command.AggregationId
import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.state.State

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
object EventPlayer {
  implicit class EventPlayerImplicit[S <: State](val events: Seq[Event[S]]) {
    def play(initialState: S) = EventPlayer.play(initialState, events)
  }

  def play[S <: State](initialState: S, events: Seq[Event[S]]): S = events.foldLeft(initialState)((s, e) => e applyTo s)
}