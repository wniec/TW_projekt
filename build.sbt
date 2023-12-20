ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "TW_projekt"
  )
libraryDependencies += "org.scalanlp" %% "breeze" % "2.1.0"