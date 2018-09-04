package akkastream.quickstart.materializedvalues

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}

import scala.concurrent.Future

object Main extends App {
  val akkaTag = HashTag("#akka")

  val tweets: Source[Tweet, NotUsed] = Source(Tweet(Author("rolandkuhn"),
    System.currentTimeMillis,
    "#akka rocks!") ::
    Tweet(Author("patriknw"), System.currentTimeMillis, "#akka !") ::
    Tweet(Author("bantonsson"), System.currentTimeMillis, "#akka !") ::
    Tweet(Author("drewhk"), System.currentTimeMillis, "#akka !") ::
    Tweet(Author("ktosopl"), System.currentTimeMillis, "#akka on the rocks!") ::
    Tweet(Author("mmartynas"), System.currentTimeMillis, "wow #akka !") ::
    Tweet(Author("akkateam"), System.currentTimeMillis, "#akka rocks!") ::
    Tweet(Author("bananaman"), System.currentTimeMillis, "#bananas rock!") ::
    Tweet(Author("appleman"), System.currentTimeMillis, "#apples rock!") ::
    Tweet(Author("drama"),
      System.currentTimeMillis,
      "we compared #apples to #oranges!") ::
    Nil)

  val count: Flow[Tweet, Int, NotUsed] = Flow[Tweet].map(_ => 1)

  val sumSink: Sink[Int, Future[Int]] = Sink.fold[Int, Int](0)(_ + _)

  val counterGraph: RunnableGraph[Future[Int]] = tweets
    .via(count)
    .toMat(sumSink)(Keep.right)

  implicit val system = ActorSystem("reactive-tweets")
  implicit val materializer = ActorMaterializer()

  val sum: Future[Int] = counterGraph.run()

  implicit val ec = system.dispatcher

  sum.foreach(c => println(s"Total tweets processed: $c"))
  sum.onComplete(_ => system.terminate())
}

final case class Author(handle: String)

final case class HashTag(name: String)

final case class Tweet(author: Author, timestamp: Long, body: String) {
  def hashTags: Set[HashTag] =
    body
      .split(" ")
      .collect {
        case t if t.startsWith("#") => HashTag(t.replaceAll("[^#\\w]", ""))
      }
      .toSet
}