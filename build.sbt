crossScalaVersions := Seq("2.10.6", "2.11.8")
lazy val commonSettings = Seq(
  organization := "com.example",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

lazy val root = (project in file("core")).
	settings(commonSettings: _*)

lazy val module1 = (project in file("module1")).
        settings(commonSettings: _*)

lazy val module2 = (project in file("module2")).
        settings(commonSettings: _*)
