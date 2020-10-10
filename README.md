# sbt-multimodule-template

Multi-module projects template using sbt.

More details at: https://www.acervera.com/blog/2020/04/sbt-crossversion-release-bintray/

## Features

### Crossversion
Generate code for different Scala versions.
This feature is enabled automatically for plugins as well, like release or publish

### Bintray publishing process
Publishing process to Bintray using [sbt/sbt-bintray](https://github.com/sbt/sbt-bintray) plugin.
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
Release process using [sbt-release](https://github.com/sbt/sbt-release) plugin.
It will release all scala versions.
```
$ sbt release
```


### Running tasks using a Scala version
```
$ sbt "++2.12.12 test" "++2.12.12 coverageReport"
```

### Coverage
Coverage has been enabled per module programmatically in the build.sbt file.

```
sbt clean "++2.13.3 test" coverageAggregate
```
#### Notes:
At the moment, `sbt-scoverage` does not working properly with cross-compile, so report aggregation is working only with
the main version. This code, will not aggregate report coverages for 2.13.3:
```
$ sbt clean "++2.13.3 test"
$ # Next is not working
$ sbt "++2.13.3 coverageAggregate"
$ # Neither next ...
$ sbt coverageAggregate
```

Not working even with 2.12.12:
```
$ sbt clean "++2.13.3 test"
$ # Next is not working
$ sbt "++2.13.3 coverageAggregate"
$ # In this case, next works
$ sbt coverageAggregate
```



