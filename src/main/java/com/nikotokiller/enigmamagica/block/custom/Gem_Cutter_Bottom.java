package com.nikotokiller.enigmamagica.block.custom;

import com.nikotokiller.enigmamagica.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Gem_Cutter_Bottom extends Block implements EntityBlock {

    protected static final VoxelShape STUMP = Block.box(1, 0, 1, 15, 11, 15);
    protected static final VoxelShape COUNTERTOP = Block.box(0, 11, 0, 16, 13, 15.6);
    protected static final VoxelShape PILLAR = Block.box(6, 0.05, 12, 10, 17, 16);
    protected static final VoxelShape SHAPE = Shapes.or(STUMP, PILLAR, COUNTERTOP);

    // NORTH
    protected static final VoxelShape STUMP_NORTH = Block.box(1, 0, 1, 15, 11, 15);
    protected static final VoxelShape COUNTERTOP_NORTH = Block.box(0, 11, 0.4, 16, 13, 16);
    protected static final VoxelShape PILLAR_NORTH = Block.box(6, 0.05, 0, 10, 17, 4);
    protected static final VoxelShape SHAPE_NORTH = Shapes.or(STUMP_NORTH, PILLAR_NORTH, COUNTERTOP_NORTH);

    // WEST
    protected static final VoxelShape STUMP_WEST = Block.box(1, 0, 1, 15, 11, 15);
    protected static final VoxelShape COUNTERTOP_WEST = Block.box(0.4, 11, 0, 16, 13, 16);
    protected static final VoxelShape PILLAR_WEST = Block.box(0, 0.05, 6, 4, 17, 10);
    protected static final VoxelShape SHAPE_WEST = Shapes.or(STUMP_WEST, PILLAR_WEST, COUNTERTOP_WEST);

    // West
    protected static final VoxelShape STUMP_EAST = Block.box(1, 0, 1, 15, 11, 15);
    protected static final VoxelShape COUNTERTOP_EAST = Block.box(0, 11, 0, 15.6, 13, 16);
    protected static final VoxelShape PILLAR_EAST = Block.box(12, 0.05, 6, 16, 17, 10);
    protected static final VoxelShape SHAPE_EAST = Shapes.or(STUMP_EAST, PILLAR_EAST, COUNTERTOP_EAST);

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public Gem_Cutter_Bottom(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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

    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving){
        super.onPlace(state, world, pos, oldState, isMoving);

        Direction facing = state.getValue(FACING);
        Block blockToPlace1 = ModBlocks.GEM_CUTTER_MIDDLE.get();
        Block blockToPlace2 = ModBlocks.GEM_CUTTER_TOP.get();

        BlockPos posAbove1 = pos.above();
        BlockPos posAbove2 = pos.above(2);

        BlockState middleState = ModBlocks.GEM_CUTTER_MIDDLE.get().defaultBlockState().setValue(FACING, facing);
        BlockState topState = ModBlocks.GEM_CUTTER_TOP.get().defaultBlockState().setValue(FACING, facing);

        if (world.isEmptyBlock(posAbove1) && world.isEmptyBlock(posAbove2)) {
            // Place the blocks
            world.setBlock(posAbove1, middleState, 3);
            world.setBlock(posAbove2, topState, 3);
        }

    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, world, pos, newState, isMoving);

        if (!isMoving) {
            BlockPos posAbove1 = pos.above();
            BlockPos posAbove2 = pos.above(2);

            // Remove the middle block if it exists
            if (world.getBlockState(posAbove1).is(ModBlocks.GEM_CUTTER_MIDDLE.get())) {
                world.destroyBlock(posAbove1, true);
            }

            // Remove the top block if it exists
            if (world.getBlockState(posAbove2).is(ModBlocks.GEM_CUTTER_TOP.get())) {
                world.destroyBlock(posAbove2, true);
            }
        }
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
