package io.github.agsmith.comicshop
package repo

import java.util.UUID

import model._

import cats.effect.IO
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
  implicit val TitleMeta: Meta[Title] = Meta[String].nxmap[Title](Title(_),_.value)
  implicit val AuthorNameMeta: Meta[AuthorName] = Meta[String].nxmap[AuthorName](AuthorName(_), _.value)
  implicit val IsbnMeta: Meta[Isbn] = Meta[String].nxmap[Isbn](Isbn(_), _.value)
  implicit val PriceMeta: Meta[Price] = Meta[String].nxmap[Price](Price(_), _.value)
  implicit val PublisherMeta: Meta[Publisher] = Meta[String].nxmap[Publisher](Publisher(_), _.value)
  implicit val OrderStatusMeta: Meta[OrderStatus] = Meta[String].nxmap[OrderStatus](OrderStatus(_), _.value)
  implicit val uuidMeta: Meta[UUID] = Meta[String].nxmap[UUID](UUID.fromString(_), _.toString)
  implicit val BoolMeta: Meta[Boolean] = Meta[String].xmap[Boolean]( _ match{
      case "true" => true
      case _ => false
    },
    _ match {
      case true => "true"
      case _ => "false"
    })

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
