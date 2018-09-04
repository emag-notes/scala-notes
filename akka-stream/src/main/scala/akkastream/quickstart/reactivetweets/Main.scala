package akkastream.quickstart.reactivetweets

import java.nio.file.Paths

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape, Graph, IOResult}
import akka.stream.scaladsl.{Broadcast, FileIO, Flow, GraphDSL, Keep, RunnableGraph, Sink, Source}
import akka.util.ByteString

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

  val authors = tweets
    .filter(_.hashTags.contains(akkaTag))
    .map(_.author)

  val hashTags: Source[HashTag, NotUsed] = tweets.mapConcat(_.hashTags.toList)

  val writeAuthors: Sink[Author, Future[IOResult]] = Flow[Author]
    .map(a => ByteString(s"${a.handle}\n"))
    .toMat(FileIO.toPath(Paths.get("target", "authors.txt")))(Keep.right)

  val writeHashTags: Sink[HashTag, Future[IOResult]] = Flow[HashTag]
    .map(t => ByteString(s"${t.name}\n"))
    .toMat(FileIO.toPath(Paths.get("target", "hashtags.txt")))(Keep.right)

  val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>
    import GraphDSL.Implicits._

    val bcast = b.add(Broadcast[Tweet](2))
    tweets ~> bcast.in
    bcast.out(0) ~> Flow[Tweet].map(_.author) ~> writeAuthors
    bcast.out(1) ~> Flow[Tweet].mapConcat(_.hashTags.toList) ~> writeHashTags
    ClosedShape
  })

  implicit val system = ActorSystem("reactive-tweets")
  implicit val materializer = ActorMaterializer()

  val result: NotUsed = g.run()
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
