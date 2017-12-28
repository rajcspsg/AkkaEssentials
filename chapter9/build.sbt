name:= "chapter5"
version:= "1.0"
scalaVersion := "2.12.4"
description := "Remote Actors"

libraryDependencies ++= Seq ("com.typesafe.akka" %% "akka-actor" % "2.5.8",
  "com.typesafe.akka" %% "akka-remote" % "2.5.8",
 "com.google.code.gson" % "gson" % "2.8.2")