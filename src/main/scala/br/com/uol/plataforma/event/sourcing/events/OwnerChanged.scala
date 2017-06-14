package br.com.uol.plataforma.event.sourcing.events

import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.state.BankAccount

case class OwnerChanged() extends Event[BankAccount] {
  def applyTo(aggregateId: String, account: BankAccount): BankAccount = {
    val newAccount = BankAccount()
    account copyTo newAccount
    newAccount.owner = this.newOwner
    newAccount
  }
}