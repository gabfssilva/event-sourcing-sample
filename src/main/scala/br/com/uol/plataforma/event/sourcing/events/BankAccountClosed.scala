package br.com.uol.plataforma.event.sourcing.events

import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.state.BankAccount

case class BankAccountClosed() extends Event[BankAccount] {
  def applyTo(aggregateId: String, account: BankAccount): BankAccount = {
    val newAccount = BankAccount()
    account copyTo newAccount
    newAccount.closeReason = this.closeReason
    newAccount.status = "CLOSED"
    newAccount
  }
}