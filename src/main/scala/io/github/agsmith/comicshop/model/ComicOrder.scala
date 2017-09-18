package io.github.agsmith.comicshop.model

import java.util.UUID

import org.joda.time.DateTime

case class ComicOrder(
  comicId: UUID,
  orderDate: DateTime,
  shipDate: Option[DateTime] = None,
  status: OrderStatus)


