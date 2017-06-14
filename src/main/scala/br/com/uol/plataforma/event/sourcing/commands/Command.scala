package br.com.uol.plataforma.event.sourcing.commands

import br.com.uol.plataforma.event.sourcing.commands.Command.AggregationId
import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.store.EventStore

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
object Command {
  type AggregationId = String
}

abstract class Command[Request, State](implicit val eventStore: EventStore[State]) extends (((AggregationId, Request, State)) => (State)) {
  type ExecutionProduce = (AggregationId, Request) => (State) => Event[State]

  def execute: ExecutionProduce

  implicit class CommandTupleImplicit(val tuple: (AggregationId, Request, State)) {
    lazy val aggregateId: String = tuple._1
    lazy val request: Request = tuple._2
    lazy val actualState: State = tuple._3
  }

  override def apply(parameters: (AggregationId, Request, State)): State = {
    val event: Event[State] = execute(parameters.aggregateId, parameters.request)(parameters.actualState)
    eventStore.add(parameters.aggregateId, event)
    event applyTo(parameters.aggregateId, parameters.actualState)
  }
}
