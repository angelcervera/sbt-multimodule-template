# sbt-multimodule-template

Multi-module projects template using sbt.

More details at: https://www.acervera.com/blog/2020/04/sbt-crossversion-release-bintray/

## Features

### Crossversion
Generate code for different Scala versions.
This feature is enabled automatically for plugins as well, like release or publish

### JFrog.

The first step it to create two repositories:
1. A local repository for Snapshots repository, in our example, called *simplexspatial-snapshots*.
2. A release repository, in our case called *simplexspatial*. This repository is going to point to our maven central repository.

This step has been integrated as part of the release process, so manual launch is not necessary.

If you prefer to execute it manually, remove ```publishArtifacts``` from the list of release steps, checkout the tag for the release that you want publish and use:
```
$ sbt publish
```

Also, you can use `sbt publish` without release **to publish snapshots**.

You can ignore artifact publication with
```
skip in publish := true
publishArtifact := false
```

Credentials are stored in `~/.sbt/.credentials`. Where user, **in the JFrog case, it is your email**:
```properties
realm=Artifactory Realm
host=simplexportal.jfrog.io
user=<insert-your-username-here>
password=<insert-your-key-here>
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
[sbt-scoverage BUG #315](https://github.com/scoverage/sbt-scoverage/issues/315)

At the moment, `sbt-scoverage` does not work properly with cross-building, so report aggregation is working only with
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
$ sbt clean "++2.12.12 test"
$ # Next is not working
$ sbt "++2.12.2 coverageAggregate"
$ # In this case, next works
$ sbt coverageAggregate
```



