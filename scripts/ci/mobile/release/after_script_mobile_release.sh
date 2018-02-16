#!/bin/bash -xe

# Upload mobile app to crashlytics
./gradlew :mobile:crashlyticsUploadDistributionDemoRelease
./gradlew :mobile:crashlyticsUploadDistributionProRelease