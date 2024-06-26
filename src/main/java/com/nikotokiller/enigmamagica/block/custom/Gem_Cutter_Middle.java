package com.nikotokiller.enigmamagica.block.custom;

import com.nikotokiller.enigmamagica.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Gem_Cutter_Middle extends Block implements EntityBlock {

    protected static final VoxelShape TOP = Block.box(6.02, 4.99, 2.1, 9.96, 15.99, 12.1);
    protected static final VoxelShape PILLAR = Block.box(6, 0, 12, 10, 16, 16);
    protected static final VoxelShape SHAPE = Shapes.or(TOP, PILLAR);

    // NORTH
    protected static final VoxelShape TOP_NORTH = Block.box(6.02, 4.99, 3.9, 9.96, 15.99, 13.9);
    protected static final VoxelShape PILLAR_NORTH = Block.box(6, 0, 0, 10, 16, 4);
    protected static final VoxelShape SHAPE_NORTH = Shapes.or(TOP_NORTH, PILLAR_NORTH);

    // WEST
    protected static final VoxelShape TOP_WEST = Block.box(3.9, 4.99, 6.02, 13.9, 15.99, 9.96);
    protected static final VoxelShape PILLAR_WEST = Block.box(0, 0, 6, 4, 16, 10);
    protected static final VoxelShape SHAPE_WEST = Shapes.or(TOP_WEST, PILLAR_WEST);

    // West
    protected static final VoxelShape TOP_EAST = Block.box(2.1, 4.99, 6.02, 12.1, 15.99, 9.96);
    protected static final VoxelShape PILLAR_EAST = Block.box(12, 0, 6, 16, 16, 10);
    protected static final VoxelShape SHAPE_EAST = Shapes.or(TOP_EAST, PILLAR_EAST);

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public Gem_Cutter_Middle(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction facing = pState.getValue(FACING);
        switch (facing) {
            case WEST:
                return SHAPE_WEST;
            case NORTH:
                return SHAPE_NORTH;
            case EAST:
                return SHAPE_EAST;
            case SOUTH:
            default:
                return SHAPE;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, world, pos, newState, isMoving);

        if (!isMoving) {
            BlockPos posBelow1 = pos.below();
            BlockPos posBelow2 = pos.below();

            // Remove the middle block if it exists
            if (world.getBlockState(posBelow1).is(ModBlocks.GEM_CUTTER_MIDDLE.get())) {
                world.destroyBlock(posBelow1, true);
            }

            // Remove the bottom block if it exists
            if (world.getBlockState(posBelow2).is(ModBlocks.GEM_CUTTER_BOTTOM.get())) {
                world.destroyBlock(posBelow2, true);
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(ModBlocks.GEM_CUTTER_BOTTOM.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        Direction direction = p_196258_1_.getHorizontalDirection();
        BlockPos blockpos = p_196258_1_.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction);
        return p_196258_1_.getLevel().getBlockState(blockpos1).canBeReplaced(p_196258_1_) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

}
