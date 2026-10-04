#!/bin/bash
# Downloads the tool jars the pipeline uses (not committed) from Maven Central.
cd "$(dirname "$0")"
M=https://repo1.maven.org/maven2
curl -fsSL -o vineflower.jar "$M/org/vineflower/vineflower/1.12.0/vineflower-1.12.0.jar"
curl -fsSL -o javaparser-core.jar "$M/com/github/javaparser/javaparser-core/3.28.2/javaparser-core-3.28.2.jar"
ls -la vineflower.jar javaparser-core.jar
