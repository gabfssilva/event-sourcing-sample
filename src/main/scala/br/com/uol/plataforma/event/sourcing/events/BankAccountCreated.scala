package br.com.uol.plataforma.event.sourcing.events

import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.state.BankAccount

case class BankAccountCreated() extends Event[BankAccount] {
  def applyTo(account: BankAccount): BankAccount = {
    val newAccount = BankAccount(account.aggregationId)

    newAccount.balance = 0
    newAccount.id = this.id
    newAccount.status = "ACTIVE"
    newAccount.owner = this.owner

    newAccount
  }
}