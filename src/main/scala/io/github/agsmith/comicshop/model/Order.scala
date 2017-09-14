package io.github.agsmith.comicshop.model

import org.joda.time.DateTime

case class Order(
  comic: ComicBook,
  orderDate: DateTime,
  shipDate: Option[DateTime] = None,
  status: OrderStatus = OrderStatus.Opened)


