package br.com.uol.plataforma.event.sourcing.state

import br.com.uol.plataforma.event.sourcing.commands.Command.AggregationId
import br.com.uol.plataforma.event.sourcing.events.BankAccountCreated
import br.com.uol.plataforma.event.sourcing.model.DynamicData

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
case class BankAccount(aggregationId: AggregationId) extends DynamicData with State