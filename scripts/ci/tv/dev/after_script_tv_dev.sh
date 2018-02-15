#!/bin/bash -xe

# Upload tv app to crashlytics
./gradlew :tv:crashlyticsUploadDistributionDemoDebug
./gradlew :tv:crashlyticsUploadDistributionProDebug