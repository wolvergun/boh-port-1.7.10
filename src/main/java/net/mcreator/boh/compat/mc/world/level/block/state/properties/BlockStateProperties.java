package net.mcreator.boh.compat.mc.world.level.block.state.properties;

import net.mcreator.boh.compat.mc.core.Axis;
import net.mcreator.boh.compat.mc.core.Direction;

public final class BlockStateProperties {
    public static final DirectionProperty FACING = DirectionProperty.create("facing");
    public static final DirectionProperty HORIZONTAL_FACING = DirectionProperty.create(
        "facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
    );
    public static final EnumProperty<Axis> AXIS = EnumProperty.create("axis", Axis.class);
    public static final EnumProperty<Axis> HORIZONTAL_AXIS = EnumProperty.create("axis", Axis.class, Axis.X, Axis.Z);
    public static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final EnumProperty<AttachFace> ATTACH_FACE = EnumProperty.create("face", AttachFace.class);
    public static final IntegerProperty AGE_7 = IntegerProperty.create("age", 0, 7);
    public static final IntegerProperty AGE_3 = IntegerProperty.create("age", 0, 3);
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 1);
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 1, 7);
    public static final BooleanProperty PERSISTENT = BooleanProperty.create("persistent");

    private BlockStateProperties() {
    }
}
