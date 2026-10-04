# boh-port-1.7.10

Backport of the famous creepypasta horror mod **The Box of Horrors** by gus (Kuro Modding) from Minecraft 1.20.1 all the way to **1.7.10** (Forge 10.13.4.1614).

Original mod: https://www.curseforge.com/minecraft/mc-mods/box-of-horrors — art by SirPancakes, Demonade, Medibird, Bigwigo and FunnyGuy_Unknown.
This is an unofficial port; see `mcmod.info` for credits.

## Project layout

| Path | Contents |
| --- | --- |
| `src/main/java/net/mcreator/boh/` | Mod code (entities, items, blocks, procedures, …) |
| `src/main/java/net/mcreator/boh/compat/` | Compatibility layer that emulates the 1.20 APIs the original MCreator code expects on top of 1.7.10 |
| `src/main/resources/assets/boh/` | Textures, models, sounds, animations, lang |
| `src/main/resources/data/boh/` | Recipes, loot tables, structures, worldgen data |
| `src/main/resources/META-INF/boh_at.cfg` | Access transformer |

## Building

The build uses the [GTNH buildscript](https://github.com/GTNewHorizons/ExampleMod1.7.10) (RetroFuturaGradle).

```sh
./gradlew build        # jar ends up in build/libs/
./gradlew runClient    # start a dev client
```

`enableModernJavaSyntax = modern` (in `gradle.properties`) compiles to **Java 25 bytecode**, matching the jar this source was
recovered from, so the mod needs a Java 25 runtime (for example via [lwjgl3ify](https://github.com/GTNewHorizons/lwjgl3ify)).
To run on a stock Java 8 1.7.10 install, switch it to `jvmDowngrader` (and set `jvmDowngraderStubsProvider`).

## Source history

This source tree was recovered by decompiling a built port jar (`boh-port.jar`, version
`master-packages-master.1+63d385dafe-dirty`) with Vineflower and remapping SRG names to MCP names using the
1.7.10 FML mappings (the same ones the buildscript uses). Names of a few dozen vanilla members that have no MCP name
remain in their `func_*`/`field_*` form, as is normal for 1.7.10 code. Comments and the original `tools/genat.sh`
helper were not recoverable.
