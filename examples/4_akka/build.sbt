name := "akkademo"

version := "0.1"

scalaVersion := "2.11.1"

libraryDependencies ++= {
  val akkaVersion = "2.3.3"
  Seq(
    "com.typesafe.akka" %% "akka-actor"   % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "org.scalatest"     %% "scalatest"    % "2.1.7"     % "test"
  )
}

