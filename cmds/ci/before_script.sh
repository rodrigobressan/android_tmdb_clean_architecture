#!/bin/bash -xe

./cmds/ci/wait_for_emulator.sh
adb shell settings put global window_animation_scale 0 &
adb shell settings put global transition_animation_scale 0 &
adb shell settings put global animator_duration_scale 0 &
adb shell input keyevent 82 &