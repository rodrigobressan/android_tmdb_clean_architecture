#!/bin/bash -xe

./gradlew :remote:test
./gradlew :cache:test
./gradlew :data:test
./gradlew :domain:test
./gradlew :presentation:test

./gradlew :mobile:connectedCheck -PdisablePreDex --stacktrace