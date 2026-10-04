#!/bin/bash
# Generates the compat base mob variants from BohMonster.java.
cd "$(dirname "$0")/.."
JDK="${JDK:-/c/Program Files/Microsoft/jdk-21.0.12.101-hotspot}"
D=port/src/main/java/net/mcreator/boh/compat/entity
gen() {
  local name=$1 super=$2 extras=$3
  sed -e "s/BohMonster/$name/g" \
      -e "s/extends net.minecraft.entity.monster.EntityMob implements/extends $super implements/" \
      -e "s/Generated variants: .*/Generated from BohMonster by tools\/genbases.sh; do not edit by hand./" \
      "$D/BohMonster.java" > "$D/$name.java"
  if [ -n "$extras" ]; then
    # drop the final closing brace and append the extras (which end with one)
    sed -i '$ d' "$D/$name.java"
    cat "tools/bundles/$extras" >> "$D/$name.java"
  fi
}
gen BohPathfinderMob net.minecraft.entity.EntityCreature ""
gen BohSpider net.minecraft.entity.monster.EntitySpider ""
gen BohAnimal net.minecraft.entity.passive.EntityAnimal base_extras_animal.txt
gen BohTamableAnimal net.minecraft.entity.passive.EntityTameable base_extras_tamable.txt
ls -la $D
