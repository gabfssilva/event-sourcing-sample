package br.com.uol.plataforma.event.sourcing.store

import scala.collection._
import scala.collection.convert.decorateAsScala._
import java.util.concurrent.ConcurrentHashMap

import br.com.uol.plataforma.event.sourcing.model.Event

class InMemoryEventStore[T] extends EventStore[T] {
  val events =
    new collection.mutable.HashMap[String, collection.mutable.Set[Event[T]]]
      with collection.mutable.MultiMap[String, Event[T]]

  override def add(aggregateId: String, event: Event[T]): Unit = events.synchronized {
    events.addBinding(aggregateId, event)
  }

  override def get(aggregateId: String): Seq[Event[T]] = events(aggregateId).toSeq.sortBy(_.eventDate)
}