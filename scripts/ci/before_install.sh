#!/bin/bash -xe

# common
chmod +x ./scripts/ci/before_install.sh
chmod +x ./scripts/ci/after_success.sh

# Mobile Dev
chmod +x ./scripts/ci/mobile/dev/install_mobile_dev.sh
chmod +x ./scripts/ci/mobile/dev/after_script_mobile_dev.sh

# Mobile QA
chmod +x ./scripts/ci/mobile/qa/install_mobile_qa.sh
chmod +x ./scripts/ci/mobile/qa/after_script_mobile_qa.sh

# TV Dev
chmod +x ./scripts/ci/tv/dev/install_tv_dev.sh
chmod +x ./scripts/ci/tv/dev/after_script_tv_dev.sh

# TV QA
chmod +x ./scripts/ci/tv/qa/install_tv_qa.sh
chmod +x ./scripts/ci/tv/qa/after_script_tv_qa.sh

chmod +x gradlew
mkdir "$ANDROID_HOME/licenses" || true
echo -e "\n8933bad161af4178b1185d1a37fbf41ea5269c55" > "$ANDROID_HOME/licenses/android-sdk-license"
echo -e "\n84831b9409646a918e30573bab4c9c91346d8abd" > "$ANDROID_HOME/licenses/android-sdk-preview-license"