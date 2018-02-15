#!/bin/bash -xe

# Upload tv app to crashlytics
./gradlew :tv:crashlyticsUploadDistributionDemoQa
./gradlew :tv:crashlyticsUploadDistributionProQa