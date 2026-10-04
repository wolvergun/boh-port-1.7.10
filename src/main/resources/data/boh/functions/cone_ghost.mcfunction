#when slender is seen
execute as @s at @s anchored eyes as @e[type=boh:ghostface,distance=..64] facing entity @s eyes anchored feet positioned ^ ^ ^1 rotated as @p positioned ^ ^ ^-1 if entity @a[distance=..1] positioned ^ ^ ^1 run effect give @s minecraft:saturation 2 0 true
