#behavior
execute as @e[type=boh:slender_man] at @s run execute as @a at @s anchored eyes facing entity @e[type=boh:slender_man,distance=..64] eyes run function boh:raycast

execute as @e[type=boh:rolling_giant] at @s run execute as @a at @s anchored eyes facing entity @e[type=boh:rolling_giant,distance=..64] eyes run function boh:raycast_roll

execute as @e[type=boh:ghostface] at @s run execute as @a at @s anchored eyes facing entity @e[type=boh:ghostface,distance=..64] eyes run function boh:raycast_ghost

