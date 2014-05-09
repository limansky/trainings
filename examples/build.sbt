scalaVersion := "2.11.0"

lazy val root = project in file(".") aggregate (futures, more)

lazy val futures = project in file("2_futures")

lazy val more = project in file("3_morescala")
