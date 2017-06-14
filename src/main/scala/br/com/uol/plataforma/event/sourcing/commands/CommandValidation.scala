package br.com.uol.plataforma.event.sourcing.commands

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
object CommandValidation {
  case class Valid(value: Boolean, message: () => String)

  def validate(validExpression: Boolean, message: => String) = Valid(validExpression, () => message)
}
