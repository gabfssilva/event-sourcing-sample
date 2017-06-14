package br.com.uol.plataforma.event.sourcing.store

import br.com.uol.plataforma.event.sourcing.state.BankAccount

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
object BankAccountEventStore {
  implicit val eventStore: EventStore[BankAccount] = new InMemoryEventStore[BankAccount]
}
