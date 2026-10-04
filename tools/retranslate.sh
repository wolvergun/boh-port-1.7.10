#!/bin/bash
# Re-runs the source translator decomp -> stage. Files hand-written in port/ (outside compat/geo) override stage.
cd "$(dirname "$0")/.."
JDK="${JDK:-/c/Program Files/Microsoft/jdk-21.0.12.101-hotspot}"
rm -rf stage/net
"$JDK/bin/java" -cp tools/javaparser-core.jar tools/translator/Translate.java decomp stage tools/translator 2>&1 | grep -v warning | tail -1
(cd port/src/main/java && find net/mcreator/boh -name "*.java" -not -path "*/compat/*" -not -path "*/geo/*") | while read f; do
  [ -f "stage/$f" ] && { [ "$f" = "net/mcreator/boh/BohMod.java" ] && mv "stage/$f" stage/BohMod.java.orig || rm "stage/$f"; }
done
true
