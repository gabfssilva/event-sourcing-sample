package br.com.uol.plataforma.event.sourcing.commands

import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.store.EventStore

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
abstract class Command[R, T](implicit val eventStore: EventStore[T]) extends (((String, R, T)) => (T)) {
  type ExecutionProduce = (String, R) => (T) => Event[T]

  def execute: ExecutionProduce

  implicit class CommandTupleImplicit(val tuple: (String, R, T)) {
    lazy val aggregateId: String = tuple._1
    lazy val request: R = tuple._2
    lazy val actualState: T = tuple._3
  }

  override def apply(parameters: (String, R, T)): T = {
    //    println("executing:" + this.getClass.getSimpleName)
    val event: Event[T] = execute(parameters.aggregateId, parameters.request)(parameters.actualState)
    eventStore.add(parameters.aggregateId, event)
    event applyTo(parameters.aggregateId, parameters.actualState)
  }
}
