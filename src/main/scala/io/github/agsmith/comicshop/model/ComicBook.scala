package io.github.agsmith.comicshop.model

import java.util.UUID

// TODO: writer/artist first and last, issue number, Price should be a float, isbn should be int?
case class ComicBook(
  id: UUID,
  title: Title,
  publisher: Publisher,
  authorName: AuthorName,
  isbn: Isbn,
  available: Boolean,
  price: Price)

