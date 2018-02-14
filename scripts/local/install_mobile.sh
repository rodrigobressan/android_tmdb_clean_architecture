#!/bin/bash -xe

adb install -r ./mobile/build/outputs/apk/demo/debug/mobile-demo-debug.apk
adb install -r ./mobile/build/outputs/apk/pro/debug/mobile-pro-debug.apk
adb install -r ./mobile/build/outputs/apk/demo/qa/mobile-demo-qa.apk
adb install -r ./mobile/build/outputs/apk/pro/qa/mobile-pro-qa.apk
