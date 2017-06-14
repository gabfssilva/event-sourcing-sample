package br.com.uol.plataforma.event.sourcing.commands

import br.com.uol.plataforma.event.sourcing.events._
import br.com.uol.plataforma.event.sourcing.model.Request
import br.com.uol.plataforma.event.sourcing.state.BankAccount
import br.com.uol.plataforma.event.sourcing.store.BankAccountEventStore._

object BankAccountCommands {
  class CreateAccountCommand extends Command[Request, BankAccount] {
    override def execute: ExecutionProduce = (request) => (_) => {
      val event = BankAccountCreated()
      event.id = request.id
      event.owner = request.owner
      event
    }

    override def validate: ValidationProduce =
      request => state => CommandValidation.validate(state.status == null, "this account is already created")
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

    override def validate: ValidationProduce =
      request => state => CommandValidation.validate((state.balance[Int] - request.amount[Int]) >= 0,
        s"the account cannot have a balance lower than zero. current balance: ${state.balance[Int]}, withdrawal amount: ${request.amount[Int]}")
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

    override def validate: ValidationProduce =
      request => state => CommandValidation.validate(!state.status.equals("CLOSED"), "this account is already closed")
  }

  type ExecutionRequest = (Request) => (BankAccount) => BankAccount

  val createAccount: ExecutionRequest = { (req) => (account) => new CreateAccountCommand().apply(req, account) }
  val withdrawal: ExecutionRequest = { (req) => (account) => new WithdrawalCommand().apply(req, account) }
  val deposit: ExecutionRequest = { (req) => (account) => new DepositCommand().apply(req, account) }
  val changeOwner: ExecutionRequest = { (req) => (account) => new ChangeOwnerCommand().apply(req, account) }
  val close: ExecutionRequest = { (req) => (account) => new CloseCommand().apply(req, account) }
}