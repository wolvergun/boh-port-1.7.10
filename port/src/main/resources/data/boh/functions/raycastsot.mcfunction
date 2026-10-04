
execute if block ~ ~ ~ #forge:transparent unless entity @e[type=boh:sotiris,dx=0] positioned ^ ^ ^.25 run function boh:raycastsot
execute if block ~ ~ ~ #forge:transparent if entity @e[type=boh:sotiris,dx=0] positioned ^ ^ ^.25 run function boh:conesot