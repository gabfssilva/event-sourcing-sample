package br.com.uol.plataforma.event.sourcing.commands

import br.com.uol.plataforma.event.sourcing.commands.Command.AggregationId
import br.com.uol.plataforma.event.sourcing.commands.CommandValidation.Valid
import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.state.State
import br.com.uol.plataforma.event.sourcing.store.EventStore

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
object Command {
  type AggregationId = String
}

abstract class Command[Request, S <: State](implicit val eventStore: EventStore[S]) extends (((Request, S)) => (S)) {
  type ExecutionProduce = (Request) => (S) => Event[S]
  type ValidationProduce = (Request) => (S) => Valid

  def execute: ExecutionProduce
  def validate: ValidationProduce = (_) => (_) => CommandValidation.validate(true, "")

  implicit class CommandTupleImplicit(val tuple: (Request, S)) {
    lazy val request: Request = tuple._1
    lazy val actualState: S = tuple._2
  }

  override def apply(parameters: (Request, S)): S = {
    val valid: Valid = validate(parameters.request)(parameters.actualState)

    if(!valid.value) {
      throw new InvalidStateException(s"you cannot apply the command to the current state: ${valid.message()}")
    }

    val aggregationId = parameters.actualState.aggregationId
    val event: Event[S] = execute(parameters.request)(parameters.actualState)
    eventStore.add(aggregationId, event)
    event applyTo(parameters.actualState)
  }
}
