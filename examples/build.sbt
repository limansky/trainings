scalaVersion := "2.11.5"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.11.5",
  organization := "com.github.limansky"
) ++ scalariformSettings

lazy val root = project in file(".") aggregate (
  fetcher, multiget,
  csvparser, calc, calcnomodel, littleparsers,
  akka, macros)

lazy val fetcher = (project in file("2_fetcher")).
  settings(commonSettings: _*)

lazy val multiget = (project in file("2_multiget")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
  )

val parsers = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"

lazy val csvparser = (project in file("3_csvparser")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += parsers
  )

lazy val calc = (project in file("3_calc")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += parsers
  )

lazy val calcnomodel = (project in file("3_calcnomodel")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += parsers
  )

lazy val littleparsers = (project in file("3_littleparsers")).
  settings(commonSettings: _*)

lazy val akka = project in file("4_akka")

lazy val macros = (project in file("5_macro")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
