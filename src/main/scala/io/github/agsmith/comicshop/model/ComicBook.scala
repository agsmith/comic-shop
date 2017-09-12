package io.github.agsmith.comicshop.model

case class Title(value: String)
case class AuthorName(value: String)
case class ISBN(value: String)
case class Price(value: String)

sealed trait Publisher
object Marvel extends Publisher
object DC extends Publisher
object DarkHorse extends Publisher

case class ComicBook(
  title: Title,
  publisher: Publisher,
  authorName: AuthorName,
  isbn: ISBN,
  price: Price)
