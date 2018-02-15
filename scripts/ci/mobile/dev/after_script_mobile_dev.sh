#!/bin/bash -xe

# Upload mobile app to crashlytics
./gradlew :mobile:crashlyticsUploadDistributionDemoDebug
./gradlew :mobile:crashlyticsUploadDistributionProDebug