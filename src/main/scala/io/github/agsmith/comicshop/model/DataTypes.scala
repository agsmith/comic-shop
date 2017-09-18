package io.github.agsmith.comicshop.model

case class Title(value: String)
object Title {
  def apply(t: Title) = t
}
case class AuthorName(value: String)
object AuthorName {
  def apply(n: AuthorName) = n
}
case class Isbn(value: String)
object Isbn {
  def apply(i: Isbn) = i
}

case class Price(value: String)
object Price {
  def apply(p: Price) = p
}
case class Publisher(value: String)
object Publisher {
  def apply(p: Publisher) = p
}

trait OrderStatus
object OrderStatus {
  def apply(o: OrderStatus) = o
}



