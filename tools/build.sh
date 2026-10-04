#!/bin/bash
# Full build: regenerate MGen + stage, stub what still fails, gradle build, copy jar to the BoH Test instance.
cd "$(dirname "$0")/.."
JDK="${JDK:-/c/Program Files/Microsoft/jdk-21.0.12.101-hotspot}"
bash tools/cycle.sh 2>&1 | grep -v busy
bash tools/stubloop.sh 2>&1 | grep -v "^unfixable" | tail -2
cd port && JAVA_HOME="$JDK" ./gradlew --no-daemon build -x test -q 2>&1 | grep -v "^warning\|^Note:" | tail -30
ls -la build/libs/
