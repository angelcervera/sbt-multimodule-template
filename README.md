# sbt-multimodule-template

Multi-module projects template using sbt.

## Features

### Crossversion
Generate code for different Scala versions.
This feature is enabled automatically for the other plugins, like release or publish

### Bintray publishing process
Publishing process to Bintray using [ sbt/sbt-bintray ](https://github.com/sbt/sbt-bintray) plugin.
This step has been integrate as part of the release process, so manual launch is not necessary.

If you prefer to execute it manually, remove ```publishArtifacts``` from the list of release steps, checkout the tag for the release that you want publish and use:
```
$ sbt publish
```
I applied several workarounds for multimodule projects.

You can ignore artifact publication with ```publishArtifact := false```

### Release process
Release process using [sbt-release](https://github.com/sbt/sbt-release) plugin
```
$ sbt release
```

