#!/bin/bash -xe

# Execute JaCoCo Test Report (later deploy to CodeCov.io)
./gradlew test jacocoTestReport

# Assemble tv QA artifact
./gradlew :tv:assembleQa --stacktrace --no-daemon

# TODO upload to firebase test lab