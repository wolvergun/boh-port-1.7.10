execute if block ~ ~ ~ #forge:transparent unless entity @e[type=boh:decoy_dog,dx=0] positioned ^ ^ ^.25 run function boh:raycastdog
execute if block ~ ~ ~ #forge:transparent if entity @e[type=boh:decoy_dog,dx=0] positioned ^ ^ ^.25 run function boh:cone_dog
