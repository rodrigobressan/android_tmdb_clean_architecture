#!/bin/bash -xe

# Upload mobile app to crashlytics
./gradlew :mobile:crashlyticsUploadDistributionDemoQa
./gradlew :mobile:crashlyticsUploadDistributionProQa