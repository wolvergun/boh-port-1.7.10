package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.context.UseOnContext;
import net.mcreator.boh.procedures.Document2011XRightClickedOnBlockProcedure;

public class Document2011XItem extends BohItem {
    public Document2011XItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        Document2011XRightClickedOnBlockProcedure.execute(
            M.getLevel(context),
            M.getX(M.getClickedPos(context)),
            M.getY(M.getClickedPos(context)),
            M.getZ(M.getClickedPos(context)),
            M.getPlayer(context),
            M.getItemInHand(context)
        );
        return InteractionResult.SUCCESS;
    }
}
