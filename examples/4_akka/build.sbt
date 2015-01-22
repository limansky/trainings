name := "akkademo"

version := "0.1"

scalaVersion := "2.11.5"

resolvers += "spray" at "http://repo.spray.io/"

libraryDependencies ++= {
  val akkaVersion = "2.3.8"
  val sprayVersion = "1.3.2"
  Seq(
    "com.typesafe.akka"         %% "akka-actor"     % akkaVersion,
    "io.spray"                  %% "spray-routing"  % sprayVersion,
    "io.spray"                  %% "spray-client"   % sprayVersion,
    "io.spray"                  %% "spray-json"     % "1.3.1",
    "com.typesafe.slick"        %% "slick"          % "2.1.0",
    "com.h2database"            %  "h2"             % "1.4.184",
    "org.slf4j"                 %  "slf4j-nop"      % "1.7.10",
    "org.scala-lang.modules"    %% "scala-xml"      % "1.0.2",
    "com.typesafe.akka"         %% "akka-testkit"   % akkaVersion   % "test",
    "org.scalatest"             %% "scalatest"      % "2.2.3"       % "test"
  )
}

