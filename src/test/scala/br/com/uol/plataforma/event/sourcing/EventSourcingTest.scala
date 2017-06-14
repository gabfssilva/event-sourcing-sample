package br.com.uol.plataforma.event.sourcing

import java.util.UUID

import br.com.uol.plataforma.event.sourcing.commands.BankAccountCommands._
import br.com.uol.plataforma.event.sourcing.model.Request
import br.com.uol.plataforma.event.sourcing.player.EventPlayer
import br.com.uol.plataforma.event.sourcing.state.BankAccount
import br.com.uol.plataforma.event.sourcing.store.BankAccountEventStore.eventStore
import org.scalatest.{FeatureSpec, Matchers}

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
class EventSourcingTest extends FeatureSpec with Matchers {
  feature("Creating an account and then closing it") {
    scenario("Create an account and then assert that replay restores the actual state of the BankAccount object") {
      val aggregateId = UUID.randomUUID().toString

      val f =
        createAccount(aggregateId, Request("owner" -> "John Doe", "id" -> 123))
          .andThen(deposit(aggregateId, Request("amount" -> 20)))
          .andThen(changeOwner(aggregateId, Request("newOwner" -> "Jane Doe")))
          .andThen(withdrawal(aggregateId, Request("amount" -> 10)))
          .andThen(close(aggregateId, Request("reason" -> "Unavailable address")))

      val actualState: BankAccount = f(BankAccount())
      val events = eventStore.get(aggregateId)
      val playedState: BankAccount = EventPlayer.play(BankAccount(), aggregateId, events)

      actualState shouldEqual playedState
    }
  }
}
