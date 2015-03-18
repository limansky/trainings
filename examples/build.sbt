scalaVersion := "2.11.6"

lazy val akkaVersion = "2.3.9"

lazy val sprayVersion = "1.3.2"

lazy val parsersLib = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"

lazy val akkaLibs = Seq(
  "com.typesafe.akka"         %% "akka-actor"                         % akkaVersion,
  "io.spray"                  %% "spray-routing"                      % sprayVersion,
  "io.spray"                  %% "spray-client"                       % sprayVersion,
  "io.spray"                  %% "spray-json"                         % "1.3.1",
  "com.typesafe.slick"        %% "slick"                              % "2.1.0",
  "com.h2database"            %  "h2"                                 % "1.4.185",
  "ch.qos.logback"            %  "logback-classic"                    % "1.1.2",
  "org.scala-lang.modules"    %% "scala-xml"                          % "1.0.3",
  "com.typesafe.akka"         %% "akka-testkit"                       % akkaVersion           % "test",
  "org.scalatest"             %% "scalatest"                          % "2.2.3"               % "test"
)

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.11.5",
  scalacOptions := Seq("-deprecation", "-unchecked", "-feature"),
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

lazy val csvparser = (project in file("3_csvparser")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += parsersLib
  )

lazy val calc = (project in file("3_calc")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += parsersLib
  )

lazy val calcnomodel = (project in file("3_calcnomodel")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += parsersLib
  )

lazy val littleparsers = (project in file("3_littleparsers")).
  settings(commonSettings: _*)

lazy val akka = (project in file("4_akka")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= akkaLibs
  )

lazy val macros = (project in file("5_macro")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
