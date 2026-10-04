#!/bin/bash
# Compile stage; stub methods that still fail; repeat until it compiles (or stops improving).
cd "$(dirname "$0")/.."
JDK="${JDK:-/c/Program Files/Microsoft/jdk-21.0.12.101-hotspot}"
J="$JDK/bin/java"
rm -f tools/stubs.txt
prev=999999
for i in 1 2 3 4 5 6 7 8; do
  out=$(bash tools/fastc.sh stage/net | tail -1)
  n=$(echo "$out" | sed 's/.*errors: //')
  echo "pass $i: $out"
  [ "$n" = "0" ] && break
  [ "$n" -ge "$prev" ] && { echo "no progress"; break; }
  prev=$n
  "$J" -cp tools/javaparser-core.jar tools/translator/StubFailing.java tools/errors.txt stage tools/stubs.txt | tail -3
done
