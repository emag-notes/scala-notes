name := "recur-fetch"

val baseSettings = Seq(
  version := "0.0.1",
  scalaVersion := "2.12.4",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

val akkaVersion     = "2.5.11"
val akkaHttpVersion = "10.0.11"
val playWsVersion   = "1.1.6"

lazy val server = project
  .in(file("server"))
  .settings(baseSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor"           % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
      "com.typesafe.akka" %% "akka-http-core"       % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
    )
  )

lazy val client = project
  .in(file("client"))
  .settings(baseSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-ahc-ws-standalone"  % playWsVersion,
      "com.typesafe.play" %% "play-ws-standalone-json" % playWsVersion
    )
  )
