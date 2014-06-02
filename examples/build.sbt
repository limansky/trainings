scalaVersion := "2.11.1"

lazy val root = project in file(".") aggregate (futures, more, akka)

lazy val futures = project in file("2_futures")

lazy val more = project in file("3_morescala")

lazy val akka = project in file("4_akka")
