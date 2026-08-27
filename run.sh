#!/bin/sh
set -eu

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
cd "$SCRIPT_DIR"

if [ ! -d bin ]; then
    echo "The bin directory does not exist. Run ./build.sh first."
    exit 1
fi

java -cp bin com.apu.hms.HospitalManagementSystem
