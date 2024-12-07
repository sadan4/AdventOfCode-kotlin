#!/usr/bin/env bash
set -e -u -o pipefail
node ./scripts/run.mjs "$@" | xargs gradle run