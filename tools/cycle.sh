#!/bin/bash
# compat compile -> regenerate MGen delegates -> retranslate -> compile compat+stage
cd "$(dirname "$0")/.."
JDK="${JDK:-/c/Program Files/Microsoft/jdk-21.0.12.101-hotspot}"
printf "package net.mcreator.boh.compat;

public class MGen {

    protected MGen() {}
}
" > port/src/main/java/net/mcreator/boh/compat/MGen.java
COMPAT_ONLY=1 bash tools/fastc.sh | tail -1
"$JDK/bin/java" tools/GenDelegates.java build-fast/classes tools/cp-fast.txt port/src/main/java/net/mcreator/boh/compat/MGen.java 2>&1 | tail -1
bash tools/retranslate.sh
bash tools/fastc.sh stage/net | tail -1
