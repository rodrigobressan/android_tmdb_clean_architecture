#!/bin/bash -xe

adb install -r ./tv/build/outputs/apk/demo/debug/tv-demo-debug.apk
adb install -r ./tv/build/outputs/apk/pro/debug/tv-pro-debug.apk
adb install -r ./tv/build/outputs/apk/demo/qa/tv-demo-qa.apk
adb install -r ./tv/build/outputs/apk/pro/qa/tv-pro-qa.apk