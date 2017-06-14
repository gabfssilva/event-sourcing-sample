package br.com.uol.plataforma.event.sourcing.model

case class Request(d: (String, Any)*) extends DynamicData(Map(d:_*))
