#!/bin/bash -xe

mkdir "$ANDROID_HOME/licenses" || true
echo "d56f5187479451eabf01fb78af6dfcb131a6481e" > "$ANDROID_HOME/licenses/android-sdk-license"
yes | sdkmanager tools
sdkmanager "system-images;android-21;default;armeabi-v7a"
echo no | avdmanager create avd --force -n test -k "system-images;android-21;default;armeabi-v7a"
$ANDROID_HOME/emulator/emulator -avd test -no-audio -no-window &
