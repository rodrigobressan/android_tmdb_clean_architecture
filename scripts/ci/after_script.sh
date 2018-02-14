#!/bin/bash -xe

./gradlew :mobile:crashlyticsUploadDistributionDemoDebug
./gradlew :mobile:crashlyticsUploadDistributionProDebug
./gradlew :mobile:crashlyticsUploadDistributionDemoQa
./gradlew :mobile:crashlyticsUploadDistributionProQa