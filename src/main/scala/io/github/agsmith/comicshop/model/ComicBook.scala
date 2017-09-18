package io.github.agsmith.comicshop.model

import java.util.UUID

case class ComicBook(
  id: UUID,
  title: Title,
  publisher: Publisher,
  authorName: AuthorName,
  isbn: Isbn,
  available: Boolean,
  price: Price)

object ComicBook {
  def apply(c: ComicBook) = c
}
