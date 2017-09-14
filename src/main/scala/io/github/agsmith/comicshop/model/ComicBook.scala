package io.github.agsmith.comicshop.model

case class ComicBook(
  title: Title,
  publisher: Publisher,
  authorName: AuthorName,
  isbn: ISBN,
  available: Boolean,
  price: Price)
