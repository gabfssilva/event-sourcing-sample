package br.com.uol.plataforma.event.sourcing.commands

import br.com.uol.plataforma.event.sourcing.model.Event
import br.com.uol.plataforma.event.sourcing.state.State

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
case class InvalidStateException(message: String) extends RuntimeException(message)
