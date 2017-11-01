#!/bin/bash -xe

./gradlew :app:check :app:connectedCheck -PdisablePreDex --stacktrace