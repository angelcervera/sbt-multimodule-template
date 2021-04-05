import sbt.Keys._
import sbt.ScmInfo

lazy val scala213 = "2.13.3"
lazy val scala212 = "2.12.12"
lazy val scala211 = "2.11.12"
lazy val scala210 = "2.10.7"
lazy val supportedScalaVersions = List(scala213, scala212, scala211, scala210)

lazy val commonSettings = Seq(
  organization := "com.acervera.multimodule",
  organizationHomepage := Some(url("http://www.acervera.com")),
  crossScalaVersions := supportedScalaVersions,
  licenses in ThisBuild += ("MIT", url("http://opensource.org/licenses/MIT")),
  homepage in ThisBuild := Some(url(s"https://github.com/angelcervera/sbt-multimodule-template")),
  scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/angelcervera/sbt-multimodule-template"), "git@github.com:angelcervera/sbt-multimodule-template.git")),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.0" % "test"
  )
)

lazy val disablingPublishingSettings =
  Seq(publish / skip := true, publishArtifact := false)

lazy val enablingPublishingSettings = Seq(
  publishArtifact := true, // Enable publish
//  publishMavenStyle := true,
  // http://www.scala-sbt.org/0.12.2/docs/Detailed-Topics/Artifacts.html
  publishArtifact in Test := false,
  credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
  publishTo := {
    if (isSnapshot.value)
      Some("Artifactory Realm" at "https://simplexportal.jfrog.io/artifactory/simplexspatial-snapshots")
    else
      Some("Artifactory Realm" at "https://simplexportal.jfrog.io/artifactory/simplexspatial")
  }
)

lazy val enablingCoverageSettings = Seq(coverageEnabled := true)

import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

lazy val root = (project in file("."))
  .aggregate(core, module1, module2, moduleIgnored, moduleOnly212)
  .settings(
    name := "sbt-multimodule-template",
    // crossScalaVersions must be set to Nil on the aggregating project
    crossScalaVersions := Nil,
    publish / skip := true,
    // don't use sbt-release's cross facility
    releaseCrossBuild := false,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      releaseStepCommandAndRemaining("+test"),
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      releaseStepCommandAndRemaining("+publish"),
      setNextVersion,
      commitNextVersion,
      pushChanges
    )
  )

lazy val core = (project in file("core"))
  .settings(
    commonSettings,
    enablingPublishingSettings,
    enablingCoverageSettings,
    name := "core",
    description := "Main project.",
  )

lazy val module1 = (project in file("module1"))
  .settings(
    commonSettings,
    enablingPublishingSettings,
    enablingCoverageSettings,
    name := "submodule1",
    description := "Submodule 1 published",
  )

lazy val module2 = (project in file("module2"))
  .settings(
    commonSettings,
    enablingPublishingSettings,
    enablingCoverageSettings,
    name := "submodule2",
    description := "Submodule 2 published",
  )

lazy val moduleIgnored = (project in file("moduleignored"))
  .settings(commonSettings, disablingPublishingSettings)

lazy val moduleOnly212 = (project in file("moduleOnly212"))
  .settings(
    commonSettings,
    crossScalaVersions := Seq(scala212),
    enablingPublishingSettings,
    enablingCoverageSettings
  )
  .dependsOn(core)
