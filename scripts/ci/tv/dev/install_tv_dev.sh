#!/bin/bash -xe

# Execute JaCoCo Test Report (later deploy to CodeCov.io)
./gradlew test jacocoTestReport

# Assemble tv debug artifact
./gradlew :tv:assembleDebug --stacktrace --no-daemon

# TODO upload TV to firebase