scalaVersion := "2.11.5"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.11.5",
  organization := "com.github.limansky"
) ++ scalariformSettings

lazy val root = project in file(".") aggregate (futures, more, akka, macros)

lazy val futures = (project in file("2_futures")).
  settings(commonSettings: _*).
  settings(
    name := "futures",
    libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
  )


lazy val more = (project in file("3_morescala")).
  settings(commonSettings: _*).
  settings(
    name := "parsers",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"
  )

lazy val akka = project in file("4_akka")

lazy val macros = (project in file("5_macro")).
  settings(commonSettings: _*).
  settings(
    name := "macros",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
