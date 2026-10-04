
execute as @p at @s anchored eyes facing entity @e[distance=..25,type=boh:decoy_dog] eyes anchored feet positioned ^ ^ ^1 rotated as @s positioned ^ ^ ^-1 if entity @s[distance=..1] run effect give @a[distance=0..25,gamemode=!creative] boh:witness 30 0 true
