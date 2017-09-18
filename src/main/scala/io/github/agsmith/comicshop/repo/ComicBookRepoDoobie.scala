package io.github.agsmith.comicshop
package repo

import java.util.UUID

import model._

import cats.data.NonEmptyList
import cats._, cats.data._, cats.effect.IO, cats.implicits._
import cats.syntax.all._

import doobie._
import doobie.implicits._
import doobie.h2.H2Transactor
import doobie.util.transactor.Transactor

sealed trait ComicBookRepoAlgebra {
  def get(id: UUID): IO[Option[ComicBook]]
  def put(comicBook: ComicBook): IO[ComicBook]
  def delete(id: UUID): IO[Option[ComicBook]]
}

class ComicBookRepoDoobie(val xa: Transactor[IO]) extends ComicBookRepoAlgebra {

   /* required implicit Meta types to handle our ADTs */
  implicit val TitleMeta: Meta[Title] = Meta[Title].nxmap(Title.apply, Title.apply)
  implicit val AuthorNameMeta: Meta[AuthorName] = Meta[AuthorName].nxmap(AuthorName.apply, AuthorName.apply)
  implicit val IsbnMeta: Meta[Isbn] = Meta[Isbn].nxmap(Isbn.apply, Isbn.apply)
  implicit val PriceMeta: Meta[Price] = Meta[Price].nxmap(Price.apply, Price.apply)
  implicit val PublisherMeta: Meta[Publisher] = Meta[Publisher].nxmap(Publisher.apply, Publisher.apply)
  implicit val OrderStatusMeta: Meta[OrderStatus] = Meta[OrderStatus].nxmap(OrderStatus.apply, OrderStatus.apply)
  implicit val ComicBookMeta: Meta[ComicBook] = Meta[ComicBook].nxmap(ComicBook.apply, ComicBook.apply)

  override def get(id: UUID): IO[Option[ComicBook]] = {
    val x = sql"SELECT * FROM ComicBook WHERE ID = ${id.toString}"
    x.query[ComicBook].option.transact(xa)
  }

  override def put(c: ComicBook): IO[ComicBook] = {
    sql"""
    INSERT INTO ComicBook(id, title, publisher, authorName, isbn, available, price)
    VALUES(${c.id.toString}, ${c.title.value}, ${c.publisher.value}, ${c.authorName.value}, ${c.isbn.value}, ${c.available}, ${c.price.value})
    """.update.run.transact(xa).map { _ => c }
  }
  override def delete(id: UUID): IO[Option[ComicBook]] = {
    get(id).flatMap {
      case Some(comic) =>
        sql"""
        DELETE ComicBook WHERE ID = ${id.toString}
        """.update.run.transact(xa).map { _ => Some(comic) }
      case _ => IO.pure(None)
    }
  }
}

object ComicBookRepoDoobie {
  def apply(): ComicBookRepoDoobie = {
    val xa = H2Transactor[IO]("jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1", "sa", "").unsafeRunSync()
    new ComicBookRepoDoobie(xa)
  }
}
