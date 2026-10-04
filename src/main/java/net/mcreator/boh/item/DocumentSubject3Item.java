package net.mcreator.boh.item;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.item.BohItem;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.item.Properties;
import net.mcreator.boh.compat.mc.world.item.Rarity;
import net.mcreator.boh.compat.mc.world.item.context.UseOnContext;
import net.mcreator.boh.procedures.DocumentSubject3RightClickedOnBlockProcedure;

public class DocumentSubject3Item extends BohItem {
    public DocumentSubject3Item() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        DocumentSubject3RightClickedOnBlockProcedure.execute(
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
