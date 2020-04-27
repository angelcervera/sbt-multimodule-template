import sbt.Keys._
import sbtrelease.ReleaseStateTransformations._

lazy val scala213 = "2.13.2"
lazy val scala212 = "2.12.11"
lazy val scala211 = "2.11.12"
lazy val scala210 = "2.10.7"
lazy val supportedScalaVersions = List(scala213, scala212, scala211, scala210)

crossScalaVersions := supportedScalaVersions

// Release
releaseCrossBuild := true
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  releaseStepCommandAndRemaining("+test"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishArtifact"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

// Avoid publish default project
publishArtifact := false

// Why Bintray is working in a not publish artifact? BUG? https://github.com/softprops/bintray-sbt/issues/93
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
  crossScalaVersions := supportedScalaVersions,
  licenses in ThisBuild += ("MIT", url("http://opensource.org/licenses/MIT"))
)

lazy val disablingPublishingSettings = Seq(skip in publish := true)

lazy val enablingPublishingSettings = Seq(
  publishArtifact := true, // Enable publish
  publishMavenStyle := true,
  // http://www.scala-sbt.org/0.12.2/docs/Detailed-Topics/Artifacts.html
  publishArtifact in Test := false,
  // Bintray
  bintrayPackageLabels := Seq("scala", "sbt"),
  bintrayRepository := "maven",
  bintrayPackage := "testing-multimodule",
//  bintrayReleaseOnPublish := false,
//  bintrayRelease := false
)

lazy val core = (project in file("core"))
  .settings(commonSettings, enablingPublishingSettings)

lazy val module1 = (project in file("module1"))
  .settings(commonSettings)

lazy val module2 = (project in file("module2"))
  .settings(commonSettings)

lazy val moduleIgnored = (project in file("moduleignored"))
  .settings(commonSettings, disablingPublishingSettings)
  .settings(
    Seq(
      publishArtifact := false // Disable publish
    )
  )
