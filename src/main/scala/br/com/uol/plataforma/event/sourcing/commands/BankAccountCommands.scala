package br.com.uol.plataforma.event.sourcing.commands

import br.com.uol.plataforma.event.sourcing.events._
import br.com.uol.plataforma.event.sourcing.model.Request
import br.com.uol.plataforma.event.sourcing.state.BankAccount
import br.com.uol.plataforma.event.sourcing.store.BankAccountEventStore._

object BankAccountCommands {
  class CreateAccountCommand extends Command[Request, BankAccount] {
    override def execute: ExecutionProduce = (request) => (state) => {
      val event = BankAccountCreated()
      event.id = request.id
      event.owner = request.owner
      event
    }
  }

  class DepositCommand extends Command[Request, BankAccount] {
    override def execute: ExecutionProduce = (request) => (_) => {
      val event = DepositPerformed()
      event.amount = request.amount
      event
    }
  }

  class WithdrawalCommand extends Command[Request, BankAccount] {
    override def execute: ExecutionProduce = (request) => (_) => {
      val event = WithdrawalPerformed()
      event.amount = request.amount
      event
    }
  }

  class ChangeOwnerCommand extends Command[Request, BankAccount] {
    override def execute: ExecutionProduce = (request) => (_) => {
      val event = OwnerChanged()
      event.newOwner = request.newOwner
      event
    }
  }

  class CloseCommand extends Command[Request, BankAccount] {
    override def execute: ExecutionProduce = (request) => (_) => {
      val event = BankAccountClosed()
      event.closeReason = request.reason
      event
    }
  }

  type ExecutionRequest = (Request) => (BankAccount) => BankAccount

  val createAccount: ExecutionRequest = { (req) => (account) => new CreateAccountCommand().apply(req, account) }
  val withdrawal: ExecutionRequest = { (req) => (account) => new WithdrawalCommand().apply(req, account) }
  val deposit: ExecutionRequest = { (req) => (account) => new DepositCommand().apply(req, account) }
  val changeOwner: ExecutionRequest = { (req) => (account) => new ChangeOwnerCommand().apply(req, account) }
  val close: ExecutionRequest = { (req) => (account) => new CloseCommand().apply(req, account) }
}