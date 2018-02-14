#!/bin/bash -xe

# Upload mobile app to crashlytics
./gradlew :mobile:crashlyticsUploadDistributionDemoDebug
./gradlew :mobile:crashlyticsUploadDistributionProDebug
./gradlew :mobile:crashlyticsUploadDistributionDemoQa
./gradlew :mobile:crashlyticsUploadDistributionProQa

# Upload tv app to crashlytics
./gradlew :tv:crashlyticsUploadDistributionDemoDebug
./gradlew :tv:crashlyticsUploadDistributionProDebug
./gradlew :tv:crashlyticsUploadDistributionDemoQa
./gradlew :tv:crashlyticsUploadDistributionProQa