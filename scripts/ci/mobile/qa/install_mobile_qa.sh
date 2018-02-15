#!/bin/bash -xe

# Execute JaCoCo Test Report (later deploy to CodeCov.io)
./gradlew test jacocoTestReport

# Assemble mobile QA artifact
./gradlew :mobile:assembleQa --stacktrace --no-daemon

# TODO upload to firebase test lab