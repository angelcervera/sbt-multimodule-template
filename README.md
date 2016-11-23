# sbt-multimodule-template

Template for multimodule projects using sbt.

## Features
### Crossversion
Generate code for different Scala versions.
This feature is enabled automatically for the other plugins, like release or publish
### Bintray publishing process
Publishin process to Bintray using [bintray-sbt](https://github.com/softprops/bintray-sbt) plugin
This step has been integrate as part of the release process, so manual launch is not necessary.

If you prefer to execute it manually, remove ```publishArtifacts``` from the list of release steps, checkout the tag for the release that you want publish and use:
```
$ sbt publish
```
I applied few workaround for multimodule projects.

You can ignore artifact with ```publishArtifact := false```
### Release process
Release process using [sbt-release](https://github.com/sbt/sbt-release) plugin
```
$ sbt release
```

