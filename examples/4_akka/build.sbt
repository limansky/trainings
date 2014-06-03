name := "akkademo"

version := "0.1"

scalaVersion := "2.11.1"

resolvers += "spray" at "http://repo.spray.io/"

libraryDependencies ++= {
  val akkaVersion = "2.3.3"
  val sprayVersion = "1.3.1-20140423"
  Seq(
    "com.typesafe.akka"         %% "akka-actor"     % akkaVersion,
    "io.spray"                  %% "spray-routing"  % sprayVersion,
    "io.spray"                  %% "spray-client"   % sprayVersion,
    "io.spray"                  %% "spray-json"     % "1.2.6",
    "com.typesafe.slick"        %% "slick"          % "2.1.0-M2",
    "com.h2database"            %  "h2"             % "1.4.178",
    "org.slf4j"                 %  "slf4j-nop"      % "1.6.4",
    "org.scala-lang.modules"    %% "scala-xml"      % "1.0.2",
    "com.typesafe.akka"         %% "akka-testkit"   % akkaVersion   % "test",
    "org.scalatest"             %% "scalatest"      % "2.1.7"       % "test"
  )
}

