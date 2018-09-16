val circeVersion = "0.9.3"
val scalaTestVersion = "3.0.5"

lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "circe",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.6",
    scalacOptions in (Compile, compile) ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Ywarn-nullary-unit",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused",
      "-Ywarn-unused-import",
      "-Ypartial-unification"
    ),
    scalacOptions in (Compile, console) += "-Ypartial-unification",
    scalacOptions in (Compile, console) -= "-Ywarn-unused:imports",
    addCompilerPlugin(
      "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies
      ++= Seq(
        "io.circe" %% "circe-core",
        "io.circe" %% "circe-generic",
        "io.circe" %% "circe-parser"
      ).map(_ % circeVersion),
    libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )
