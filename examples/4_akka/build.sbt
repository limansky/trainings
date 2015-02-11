name := "akkademo"

version := "0.1"

scalaVersion := "2.11.5"

libraryDependencies ++= {
  val akkaVersion = "2.3.9"
  val akkaStreamVersion = "1.0-M3"
  Seq(
    "com.typesafe.akka"         %% "akka-actor"                         % akkaVersion,
    "com.typesafe.akka"         %% "akka-http-experimental"             % akkaStreamVersion,
    "com.typesafe.akka"         %% "akka-http-spray-json-experimental"  % akkaStreamVersion,
    "com.typesafe.slick"        %% "slick"                              % "2.1.0",
    "com.h2database"            %  "h2"                                 % "1.4.185",
    "ch.qos.logback"            %  "logback-classic"                    % "1.1.2",
    "org.scala-lang.modules"    %% "scala-xml"                          % "1.0.3",
    "com.typesafe.akka"         %% "akka-testkit"                       % akkaVersion           % "test",
    "org.scalatest"             %% "scalatest"                          % "2.2.3"               % "test"
  )
}

