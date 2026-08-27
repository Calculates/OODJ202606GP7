#!/bin/sh
set -eu

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
cd "$SCRIPT_DIR"

mkdir -p bin
find src -name '*.java' -print0 | xargs -0 javac -d bin -sourcepath src

echo "Build successful. Run ./run.sh to start the application."
