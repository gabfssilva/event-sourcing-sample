package br.com.uol.plataforma.event.sourcing

import java.util.UUID

import br.com.uol.plataforma.event.sourcing.commands.BankAccountCommands._
import br.com.uol.plataforma.event.sourcing.model.{Event, Request}
import br.com.uol.plataforma.event.sourcing.player.EventPlayer._
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
        createAccount(Request("owner" -> "John Doe", "id" -> 123))
          .andThen(deposit(Request("amount" -> 20)))
          .andThen(changeOwner(Request("newOwner" -> "Jane Doe")))
          .andThen(withdrawal(Request("amount" -> 10)))
          .andThen(close(Request("reason" -> "Unavailable address")))

      val actualState: BankAccount = f(BankAccount(aggregateId))
      val events: Seq[Event[BankAccount]] = eventStore.get(aggregateId)
      val playedState: BankAccount = events.play(BankAccount(aggregateId))

      actualState shouldEqual playedState
    }
  }
}
