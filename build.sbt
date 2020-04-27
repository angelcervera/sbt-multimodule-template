import sbt.Keys._
import sbt.ScmInfo

lazy val scala213 = "2.13.2"
lazy val scala212 = "2.12.11"
lazy val scala211 = "2.11.12"
lazy val scala210 = "2.10.7"
lazy val supportedScalaVersions = List(scala213, scala212, scala211, scala210)

crossScalaVersions := supportedScalaVersions

lazy val commonSettings = Seq(
  organization := "com.acervera.multimodule",
  organizationHomepage := Some(url("http://www.acervera.com")),
  crossScalaVersions := supportedScalaVersions,
  licenses in ThisBuild += ("MIT", url("http://opensource.org/licenses/MIT")),
  homepage in ThisBuild := Some(url(s"https://github.com/angelcervera/sbt-multimodule-template")),
  scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/angelcervera/sbt-multimodule-template"), "git@github.com:angelcervera/sbt-multimodule-template.git")),
)

lazy val disablingPublishingSettings =
  Seq(skip in publish := true, publishArtifact := false)

lazy val enablingPublishingSettings = Seq(
  publishArtifact := true, // Enable publish
  publishMavenStyle := true,
  // http://www.scala-sbt.org/0.12.2/docs/Detailed-Topics/Artifacts.html
  publishArtifact in Test := false,
  // Bintray
  bintrayPackageLabels := Seq("scala", "sbt"),
  bintrayRepository := "maven",
  bintrayVcsUrl := Some("https://github.com/angelcervera/sbt-multimodule-template.git"),
//  bintrayReleaseOnPublish := false, To enable staging
//  bintrayRelease := false
)

import ReleaseTransformations._
lazy val root = (project in file("."))
  .aggregate(core, module1, module2, moduleIgnored)
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
    name := "core",
    description := "Main project.",
    bintrayPackage := "multimodule-core", // Package name that the Bintray Dashboard will show.
  )

lazy val module1 = (project in file("module1"))
  .settings(
    commonSettings,
    enablingPublishingSettings,
    name := "submodule1",
    description := "Submodule 1 published",
    bintrayPackage := "multimodule-submodule-1",
  )

lazy val module2 = (project in file("module2"))
  .settings(
    commonSettings,
    enablingPublishingSettings,
    name := "submodule2",
    description := "Submodule 2 published",
    bintrayPackage := "multimodule-submodule-2",
  )

lazy val moduleIgnored = (project in file("moduleignored"))
  .settings(commonSettings, disablingPublishingSettings)
