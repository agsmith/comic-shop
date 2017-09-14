package io.github.agsmith.comicshop.model

case class Title(value: String)
case class AuthorName(value: String)
case class ISBN(value: String)
case class Price(value: String)

sealed trait Publisher
object Publisher {
  object Marvel extends Publisher
  object DC extends Publisher
  object DarkHorse extends Publisher
}

sealed trait OrderStatus
object OrderStatus {
  object Opened extends OrderStatus
  object Shipped extends OrderStatus
  object Completed extends OrderStatus
}


