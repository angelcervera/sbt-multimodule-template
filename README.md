# sbt-multimodule-template

Multi-module projects template using sbt.

## Features

### Crossversion
Generate code for different Scala versions.
This feature is enabled automatically for plugins as well, like release or publish

### Bintray publishing process
Publishing process to Bintray using [ sbt/sbt-bintray ](https://github.com/sbt/sbt-bintray) plugin.
This step has been integrate as part of the release process, so manual launch is not necessary.

If you prefer to execute it manually, remove ```publishArtifacts``` from the list of release steps, checkout the tag for the release that you want publish and use:
```
$ sbt publish
```

You can ignore artifact publication with
```
skip in publish := true
publishArtifact := false
```

Bintray credentials are stored in `~/.bintray/.credentials` but [other ways are allowed](https://github.com/sbt/sbt-bintray#credentials):

```properties
realm = Bintray API Realm
host = api.bintray.com
user = <insert-your-username-here>
password = <insert-your-key-here>
```

### Release process
Release process using [sbt-release](https://github.com/sbt/sbt-release) plugin
```
$ sbt release
```


