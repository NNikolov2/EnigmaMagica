package com.nikotokiller.enigmamagica.block.custom;

import com.nikotokiller.enigmamagica.block.ModBlocks;
import com.nikotokiller.enigmamagica.block.entity.GemCutterEntity;
import com.nikotokiller.enigmamagica.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class Gem_Cutter_Middle extends BaseEntityBlock{

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

    // East
    protected static final VoxelShape TOP_EAST = Block.box(2.1, 4.99, 6.02, 12.1, 15.99, 9.96);
    protected static final VoxelShape PILLAR_EAST = Block.box(12, 0, 6, 16, 16, 10);
    protected static final VoxelShape SHAPE_EAST = Shapes.or(TOP_EAST, PILLAR_EAST);

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public Gem_Cutter_Middle(Properties pProperties) {
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
        return new GemCutterEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.GEM_CUTTER_ENTITY.get(), (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockPos posTop = pPos.above();
            BlockEntity entity = pLevel.getBlockEntity(posTop);
            if (entity instanceof GemCutterEntity) {
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (GemCutterEntity) entity, posTop);
            } else {
                throw new IllegalStateException("Container provider is missing.");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GemCutterEntity) {
                ((GemCutterEntity) blockEntity).drops();
            }
        }

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
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        BlockPos blockPos = pContext.getClickedPos();
        BlockPos blockPos1 = blockPos.relative(direction);
        return pContext.getLevel().getBlockState(blockPos1).canBeReplaced(pContext) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

}
