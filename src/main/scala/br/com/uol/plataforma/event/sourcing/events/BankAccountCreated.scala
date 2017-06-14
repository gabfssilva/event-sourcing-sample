package br.com.uol.plataforma.event.sourcing.events

import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.state.BankAccount

case class BankAccountCreated() extends Event[BankAccount] {
  def applyTo(aggregateId: String, account: BankAccount): BankAccount = {
    account.balance = 0
    account.id = this.id
    account.status = "ACTIVE"
    account.owner = this.owner
    account
  }
}