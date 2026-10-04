execute if block ~ ~ ~ #forge:transparent unless entity @e[type=boh:slender_man,dx=0] positioned ^ ^ ^.25 run function boh:raycast
execute if block ~ ~ ~ #forge:transparent if entity @e[type=boh:slender_man,dx=0] positioned ^ ^ ^.25 run function boh:cone
