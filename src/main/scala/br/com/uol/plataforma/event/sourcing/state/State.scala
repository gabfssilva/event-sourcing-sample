package br.com.uol.plataforma.event.sourcing.state

import br.com.uol.plataforma.event.sourcing.commands.Command.AggregationId

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
trait State {
  val aggregationId: AggregationId
}
