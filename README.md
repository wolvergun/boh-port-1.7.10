# boh-port-1.7.10

Unofficial port of **The Box of Horrors** (1.20.1 Forge, made with MCreator + GeckoLib, by gus / Kuro Modding) to
**Minecraft 1.7.10** (Forge 10.13.4.1614). The goal is a full port that keeps all original content: mobs, items,
blocks, effects, overlays, dimensions, biomes and structures.

Original mod: https://www.curseforge.com/minecraft/mc-mods/box-of-horrors (art by SirPancakes, Demonade, Medibird,
Bigwigo and FunnyGuy_Unknown). Made for the SnyderCraft modpack.

## Licensing

- The original mod is **CC BY-NC**: keep the credits, non-commercial use only.
- **KuroLIB is All Rights Reserved.** Never copy its code; its effects are reimplemented from scratch.
- Don't bundle Mojang's 1.20 assets, and never commit Mojang's decompiled Minecraft source (`ref/` is gitignored).

## Repository layout

| Path | What it is |
| --- | --- |
| `port/` | The Gradle project (GTNH buildscript / RetroFuturaGradle). Hand-written code lives in `port/src/main/java`. |
| `port/src/main/java/net/mcreator/boh/compat/` | Compatibility layer emulating the 1.20 APIs on 1.7.10: `M*` static helpers (`M → MEvent → MEntity → MWorld → MItem → MCore → MGen`, `MGen` is generated), base classes (`BohMonster`, `BohBlock`, `BohItem`, …), event bridges, client rendering shims, worldgen in `compat/world/gen`. |
| `port/src/main/java/net/mcreator/boh/geo/` | GeckoLib reimplementation |
| `port/src/main/resources/` | Converted assets, data, `mcmod.info`, access transformer |
| `decomp/` | The original 1.20.1 jar decompiled with Vineflower (translator input) |
| `stage/` | **Generated.** Translator output, compiled together with `port/` (see `port/build.gradle.kts`). Files in `port/` outside `compat/`/`geo/` override their `stage/` counterpart. |
| `tools/translator/` | JavaParser-based translator (`Translate.java`) and its config: `types.map`, `replace.txt`, `fields.map`, `keep.txt`, `force.txt`, … |
| `tools/*.sh` | Pipeline scripts (see below) |

Not in git (keep them locally): `original/` (gus's jar + extracted assets), `ref/` (MC 1.7.10 / MCP / GeckoLib
references), `build-fast/`, tool jars, and the machine-specific `tools/classpath.txt` / `tools/cp-fast.txt`.

## Building

**Easiest:** every push is built by GitHub Actions (`.github/workflows/build.yml`), and the jar is published as
`boh-port.jar` on the [dev-build release](https://github.com/wolvergun/boh-port-1.7.10/releases/tag/dev-build).

Requirements: JDK 21 to run Gradle; the build compiles with a Java 25 toolchain (`enableModernJavaSyntax = modern`),
so the jar is **Java 25 bytecode** and needs a Java 25 runtime (lwjgl3ify + RetroFuturaBootstrap). It won't load on
Java 8.

```sh
cd port && ./gradlew build -x test     # jar: port/build/libs/boh-<version>.jar (not the -dev / -sources ones)
```

Full pipeline (Git Bash on Windows; set `JDK` if your JDK 21 isn't at the default path in the scripts):

```sh
bash tools/fetch-tools.sh   # once: downloads vineflower.jar + javaparser-core.jar
bash tools/build.sh         # cycle.sh (MGen + retranslate) -> stubloop.sh (stub what won't compile) -> gradle build
```

`tools/fastc.sh` needs `tools/cp-fast.txt` (the compile classpath, `;`-separated). Generate it with
`cd port && ./gradlew -I ../tools/printcp.gradle printCp`, which writes `tools/classpath.txt`, then copy it to
`tools/cp-fast.txt`.

## Test environment

- Prism instance **"BoH Test"**: Forge 10.13.4.1614 on Java 25 via lwjgl3ify + RetroFuturaBootstrap, with Angelica,
  EndlessIDs, UniMixins, GTNHLib, Hodgepodge and Fisk's Superheroes. Install the built jar as `mods/boh-port.jar`.
- **SnyderCraft** uses the same stack. **Never modify the SnyderCraft instance; test only in BoH Test.**
- lwjgl3ify needs every enum that mods extend at runtime listed under `extensibleEnums` in
  `config/lwjgl3ify-early.json`. Fisk's mod needs `net.minecraftforge.client.IItemRenderer$ItemRenderType` there.

Debug commands:

- `/bohdebug dim <level_0|baseplate_dimension|boiler_room_dimension|gaster_dimension|overworld>`: teleport between dimensions
- `/bohdebug effect <name>`: apply a mod effect
- `/bohdebug inspect [radius]`: dump the server-side state of the nearest mob (health, death timer, hitbox, AI target, running goals)

## Status

Working: all mobs, items and blocks register and render; mod effects, including the 35 screen overlays; natural mob
spawning; the four dimensions (Level 0 / Backrooms maze, Baseplate, Boiler Room, Gaster's room) and 5 biomes
(Shrouded Cliffs spawns in the overworld); structures loaded from the original `.nbt` files.

Compatibility fixes to know about:

- **EndlessIDs:** the vanilla biome-array accessors crash under it, so the mod uses its `setBiomeShortArray`.
- **Fisk's Superheroes** leaves scoreboard scores without an objective, which crash world saving. A safety net
  (`compat/world/SafeScoreboardSave`) clears them before saving.
- **Dedicated servers:** common code must never make the JVM load a `net.minecraft.client` class (FML refuses it on
  the server, and the verifier loads classes for assignability checks, not only when code runs). Helpers with client
  types in their signature are `@SideOnly(Side.CLIENT)`. The `Self-test` workflow boots a dedicated server on every push.

### To do (priority order)

1. **Lifeform**: spawned from an egg it lies flat, can't be hit and never attacks (probably dead but never removed).
   Fix it, then check the other mobs for the same problem.
2. Level 0 ambience (sound loop, mood sounds, music, ash particles); the Boiler Room's random Freddy laugh; Shrouded
   Cliffs' fog colour in the overworld.
3. Baseplate, Boiler Room and Gaster's room: the CI self-test now checks they generate whole and that arriving puts
   you inside the room (structures wider than 2x2 chunks used to be cut off, and arrival used to miss the room).
   Still to check in-game: those rooms, plus the overworld structures (Sadako well, Siren Head nests, forest
   structures).
4. ~~Apply villager trades and brewing recipes~~: hooked up (trade handler + PotionBrewEvent), needs in-game testing.
5. Polish arm poses and 3D weapon positioning; optionally make the Xenomorph's see-through head solid.
6. Some mobs don't attack the player or other mobs: target goals are now ports of the 1.20 ones (line of sight to
   acquire, 15 s revenge memory, ...) and ground pathfinding is capped at 64 blocks; needs in-game testing.
