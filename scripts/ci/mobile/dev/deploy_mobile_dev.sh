#!/bin/sh

generate_post_data()
{
  cat <<EOF
{
	"request": {
		"message": "Deploy Mobile Dev script called",
		"branch": "dev",
		"config": {
			"merge_mode": "deep_merge",
			"script": "./scripts/ci/mobile/debug/install_mobile_dev.sh",
			"after_script": "./scripts/ci/mobile/debug/after_script_mobile_dev.sh"
		}
	}
}
EOF
}

curl -s -X POST \
 -H "Content-Type: application/json" \
 -H "Accept: application/json" \
 -H "Travis-API-Version: 3" \
 -H "Authorization: token ${travis_api_token}" \
 -d "$(generate_post_data)" \
 https://api.travis-ci.org/repo/bresan%2Fandroid_tmdb_clean_architecture/requests