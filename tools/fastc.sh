#!/bin/bash
# Fast javac over port sources (+ optional extra source roots) -> errors in tools/errors.txt
# COMPAT_ONLY=1: compile only the compat/geo layer with a stub BohMod (used to regenerate MGen).
cd "$(dirname "$0")/.."
JDK="${JDK:-/c/Program Files/Microsoft/jdk-21.0.12.101-hotspot}"
JAVAC="$JDK/bin/javac"
rm -rf build-fast && mkdir -p build-fast
if [ -n "$COMPAT_ONLY" ]; then
  find port/src/main/java/net/mcreator/boh/compat port/src/main/java/net/mcreator/boh/geo -name "*.java" > build-fast/sources.txt
  printf 'package net.mcreator.boh; public class BohMod { public static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger("boh"); public static BohMod instance; public static final String MODID = "boh"; }' > build-fast/BohMod.java
  echo build-fast/BohMod.java >> build-fast/sources.txt
else
  find port/src/main/java "$@" -name "*.java" > build-fast/sources.txt
fi
echo 'package net.mcreator.boh; public class Tags { public static final String VERSION = "dev"; }' > build-fast/Tags.java
echo build-fast/Tags.java >> build-fast/sources.txt
"$JAVAC" -J-Xmx6g -encoding UTF-8 --release 21 -proc:none -nowarn -Xmaxerrs 100000 -cp "$(cat tools/cp-fast.txt)" -d build-fast/classes @build-fast/sources.txt > tools/errors.txt 2>&1
echo "exit $? ; errors: $(grep -c ': error:' tools/errors.txt)"
