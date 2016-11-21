import sbt.Keys._
import sbtrelease.ReleaseStateTransformations._

crossScalaVersions := Seq("2.10.6", "2.11.8")

// Release
releaseCrossBuild := true
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  pushChanges
)

// Avoid publish default project
publishArtifact := false

// Why Bintray is working in a not publish artifact? BUG?
// Workaround 1:
//licenses += ("MIT", url("http://opensource.org/licenses/MIT")) // bintrayEnsureLicenses / Removing this, error even with publishArtifact := false
//bintrayReleaseOnPublish := false // If not, try to release a non published artifact
//bintrayPackage := "testing-multimodule" // If not present, create an empty package with the name of the project.
//version := "0.1.0" // If it is not present, bintray pluging fail because the default version is SNAPSHOT
// Workaround 2:
//bintrayRelease := false
//bintrayEnsureBintrayPackageExists := false
//bintrayEnsureLicenses := false

lazy val commonSettings = Seq(
  organization := "com.acervera",
  scalaVersion := "2.11.8",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  publishArtifact := true, // Enable publish
  publishMavenStyle := true,

  // Bintray
  bintrayRepository := "maven",
  bintrayPackage := "testing-multimodule",
  bintrayReleaseOnPublish := false,
  bintrayRelease := false
)

lazy val core = (project in file("core")).
  settings(commonSettings: _*)

lazy val module1 = (project in file("module1")).
  settings(commonSettings: _*)

lazy val module2 = (project in file("module2")).
  settings(commonSettings: _*)

lazy val moduleIgnored = (project in file("moduleignored")).
  settings(commonSettings: _*).
  settings(
    Seq(
      publishArtifact := false // Disable publish
    )
  )
