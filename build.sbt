name := "AkkaEssentials"

version := "1.0"

scalaVersion := "2.12.4"

lazy val chapter2 = (project in file("chapter2")).settings(
  name := "chapter2",
  version := "1.0",
  scalaVersion := "2.12.4",
  description := "Starting with Akka"
)

lazy val chapter3 = (project in file("chapter3")).settings(
  name:= "chapter3",
  version := "1.0",
  scalaVersion := "2.12.4",
  description := "Actors"
)


lazy val chapter4 = (project in file("chapter4")).settings(
  name:= "chapter4",
  version:= "1.0",
  scalaVersion := "2.12.4",
  description := "Typed Actors"
)


lazy val chapter5 = (project in file("chapter5")).settings(
  name:= "chapter5",
  version:= "1.0",
  scalaVersion := "2.12.4",
  description := "Routers And Dispatchers"
)
