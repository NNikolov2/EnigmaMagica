package com.nikotokiller.enigmamagica.block.custom;

import com.nikotokiller.enigmamagica.block.entity.GemFusionEntity;
import com.nikotokiller.enigmamagica.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

public class Gem_Fusion_Table extends BaseEntityBlock {

    protected static final VoxelShape LEG1 = Block.box(12, 0, 2, 14, 10, 4);
    protected static final VoxelShape LEG2 = Block.box(2, 0, 2, 4, 10, 4);
    protected static final VoxelShape LEG3 = Block.box(2, 0, 12, 4, 10, 14);
    protected static final VoxelShape LEG4 = Block.box(12, 0, 12, 14, 10, 14);
    protected static final VoxelShape TOP = Block.box(0, 9, 0, 16, 11, 16);
    protected static final VoxelShape BOTTOMSIDE1 = Block.box(3, 7, 12, 13, 9, 13);
    protected static final VoxelShape BOTTOMSIDE2 = Block.box(12, 7, 3, 13, 9, 13);
    protected static final VoxelShape BOTTOMSIDE3 = Block.box(3, 7, 3, 4, 9, 13);
    protected static final VoxelShape BOTTOMSIDE4 = Block.box(3, 7, 3, 13, 9, 4);
    protected static final VoxelShape HOLDER1 = Block.box(1, 11, 6, 3, 17, 10);
    protected static final VoxelShape HOLDER2 = Block.box(13, 11, 6, 15, 17, 10);
    protected static final VoxelShape SPINNER1 = Block.box(-1, 14, 7, 5, 16, 9);
    protected static final VoxelShape SPINNER2 = Block.box(11, 14, 7, 17, 16, 9);
    protected static final VoxelShape BASIN1 = Block.box(5, 13, 7, 6, 17, 9);
    protected static final VoxelShape BASIN2 = Block.box(10, 13, 7, 11, 17, 9);
    protected static final VoxelShape BASIN3 = Block.box(9, 12, 6, 10, 18, 10);
    protected static final VoxelShape BASIN4 = Block.box(6, 12, 6, 7, 18, 10);
    protected static final VoxelShape HANDLE1 = Block.box(-3, 13, 6.75, -1, 17, 9.25);
    protected static final VoxelShape HANDLE2 = Block.box(17, 13, 6.75, 19, 17, 9.25);

    protected static final VoxelShape SHAPE = Shapes.or(
            LEG1, LEG2, LEG3, LEG4,
            TOP,
            BOTTOMSIDE1, BOTTOMSIDE2, BOTTOMSIDE3, BOTTOMSIDE4,
            HOLDER1, HOLDER2,
            SPINNER1, SPINNER2,
            BASIN1, BASIN2, BASIN3, BASIN4,
            HANDLE1, HANDLE2
    );

    // NORTH Rotation
    protected static final VoxelShape LEG1_NORTH = Block.box(12, 0, 2, 14, 10, 4);
    protected static final VoxelShape LEG2_NORTH = Block.box(2, 0, 2, 4, 10, 4);
    protected static final VoxelShape LEG3_NORTH = Block.box(2, 0, 12, 4, 10, 14);
    protected static final VoxelShape LEG4_NORTH = Block.box(12, 0, 12, 14, 10, 14);
    protected static final VoxelShape TOP_NORTH = Block.box(0, 9, 0, 16, 11, 16);
    protected static final VoxelShape BOTTOMSIDE1_NORTH = Block.box(3, 7, 12, 13, 9, 13);
    protected static final VoxelShape BOTTOMSIDE2_NORTH = Block.box(12, 7, 3, 13, 9, 13);
    protected static final VoxelShape BOTTOMSIDE3_NORTH = Block.box(3, 7, 3, 4, 9, 13);
    protected static final VoxelShape BOTTOMSIDE4_NORTH = Block.box(3, 7, 3, 13, 9, 4);
    protected static final VoxelShape HOLDER1_NORTH = Block.box(1, 11, 6, 3, 17, 10);
    protected static final VoxelShape HOLDER2_NORTH = Block.box(13, 11, 6, 15, 17, 10);
    protected static final VoxelShape SPINNER1_NORTH = Block.box(-1, 14, 7, 5, 16, 9);
    protected static final VoxelShape SPINNER2_NORTH = Block.box(11, 14, 7, 17, 16, 9);
    protected static final VoxelShape BASIN1_NORTH = Block.box(5, 13, 7, 6, 17, 9);
    protected static final VoxelShape BASIN2_NORTH = Block.box(10, 13, 7, 11, 17, 9);
    protected static final VoxelShape BASIN3_NORTH = Block.box(9, 12, 6, 10, 18, 10);
    protected static final VoxelShape BASIN4_NORTH = Block.box(6, 12, 6, 7, 18, 10);
    protected static final VoxelShape HANDLE1_NORTH = Block.box(-3, 13, 6.75, -1, 17, 9.25);
    protected static final VoxelShape HANDLE2_NORTH = Block.box(17, 13, 6.75, 19, 17, 9.25);

    protected static final VoxelShape SHAPE_NORTH = Shapes.or(
            LEG1_NORTH, LEG2_NORTH, LEG3_NORTH, LEG4_NORTH,
            TOP_NORTH,
            BOTTOMSIDE1_NORTH, BOTTOMSIDE2_NORTH, BOTTOMSIDE3_NORTH, BOTTOMSIDE4_NORTH,
            HOLDER1_NORTH, HOLDER2_NORTH,
            SPINNER1_NORTH, SPINNER2_NORTH,
            BASIN1_NORTH, BASIN2_NORTH, BASIN3_NORTH, BASIN4_NORTH,
            HANDLE1_NORTH, HANDLE2_NORTH
    );

    // WEST Rotation
    protected static final VoxelShape LEG1_WEST = Block.box(2, 0, 12, 4, 10, 14);
    protected static final VoxelShape LEG2_WEST = Block.box(2, 0, 2, 4, 10, 4);
    protected static final VoxelShape LEG3_WEST = Block.box(12, 0, 2, 14, 10, 4);
    protected static final VoxelShape LEG4_WEST = Block.box(12, 0, 12, 14, 10, 14);
    protected static final VoxelShape TOP_WEST = Block.box(0, 9, 0, 16, 11, 16);
    protected static final VoxelShape BOTTOMSIDE1_WEST = Block.box(12, 7, 3, 13, 9, 13);
    protected static final VoxelShape BOTTOMSIDE2_WEST = Block.box(3, 7, 12, 13, 9, 13);
    protected static final VoxelShape BOTTOMSIDE3_WEST = Block.box(3, 7, 3, 13, 9, 4);
    protected static final VoxelShape BOTTOMSIDE4_WEST = Block.box(3, 7, 3, 4, 9, 13);
    protected static final VoxelShape HOLDER1_WEST = Block.box(6, 11, 13, 10, 17, 15);
    protected static final VoxelShape HOLDER2_WEST = Block.box(6, 11, 1, 10, 17, 3);
    protected static final VoxelShape SPINNER1_WEST = Block.box(7, 14, -1, 9, 16, 5);
    protected static final VoxelShape SPINNER2_WEST = Block.box(7, 14, 11, 9, 16, 17);
    protected static final VoxelShape BASIN1_WEST = Block.box(7, 13, 5, 9, 17, 6);
    protected static final VoxelShape BASIN2_WEST = Block.box(7, 13, 10, 9, 17, 11);
    protected static final VoxelShape BASIN3_WEST = Block.box(6, 12, 9, 10, 18, 10);
    protected static final VoxelShape BASIN4_WEST = Block.box(6, 12, 6, 10, 18, 7);
    protected static final VoxelShape HANDLE1_WEST = Block.box(6.75, 13, -3, 9.25, 17, -1);
    protected static final VoxelShape HANDLE2_WEST = Block.box(6.75, 13, 17, 9.25, 17, 19);

    protected static final VoxelShape SHAPE_WEST = Shapes.or(
            LEG1_WEST, LEG2_WEST, LEG3_WEST, LEG4_WEST,
            TOP_WEST,
            BOTTOMSIDE1_WEST, BOTTOMSIDE2_WEST, BOTTOMSIDE3_WEST, BOTTOMSIDE4_WEST,
            HOLDER1_WEST, HOLDER2_WEST,
            SPINNER1_WEST, SPINNER2_WEST,
            BASIN1_WEST, BASIN2_WEST, BASIN3_WEST, BASIN4_WEST,
            HANDLE1_WEST, HANDLE2_WEST
    );

    // EAST Rotation
    protected static final VoxelShape LEG1_EAST = Block.box(12, 0, 12, 14, 10, 14);
    protected static final VoxelShape LEG2_EAST = Block.box(12, 0, 2, 14, 10, 4);
    protected static final VoxelShape LEG3_EAST = Block.box(2, 0, 2, 4, 10, 4);
    protected static final VoxelShape LEG4_EAST = Block.box(2, 0, 12, 4, 10, 14);
    protected static final VoxelShape TOP_EAST = Block.box(0, 9, 0, 16, 11, 16);
    protected static final VoxelShape BOTTOMSIDE1_EAST = Block.box(3, 7, 12, 13, 9, 13);
    protected static final VoxelShape BOTTOMSIDE2_EAST = Block.box(3, 7, 3, 4, 9, 13);
    protected static final VoxelShape BOTTOMSIDE3_EAST = Block.box(3, 7, 3, 13, 9, 4);
    protected static final VoxelShape BOTTOMSIDE4_EAST = Block.box(12, 7, 3, 13, 9, 13);
    protected static final VoxelShape HOLDER1_EAST = Block.box(6, 11, 1, 10, 17, 3);
    protected static final VoxelShape HOLDER2_EAST = Block.box(6, 11, 13, 10, 17, 15);
    protected static final VoxelShape SPINNER1_EAST = Block.box(7, 14, 11, 9, 16, 17);
    protected static final VoxelShape SPINNER2_EAST = Block.box(7, 14, -1, 9, 16, 5);
    protected static final VoxelShape BASIN1_EAST = Block.box(7, 13, 10, 9, 17, 11);
    protected static final VoxelShape BASIN2_EAST = Block.box(7, 13, 5, 9, 17, 6);
    protected static final VoxelShape BASIN3_EAST = Block.box(6, 12, 6, 10, 18, 7);
    protected static final VoxelShape BASIN4_EAST = Block.box(6, 12, 9, 10, 18, 10);
    protected static final VoxelShape HANDLE1_EAST = Block.box(6.75, 13, 17, 9.25, 17, 19);
    protected static final VoxelShape HANDLE2_EAST = Block.box(6.75, 13, -3, 9.25, 17, -1);

    protected static final VoxelShape SHAPE_EAST = Shapes.or(
            LEG1_EAST, LEG2_EAST, LEG3_EAST, LEG4_EAST,
            TOP_EAST,
            BOTTOMSIDE1_EAST, BOTTOMSIDE2_EAST, BOTTOMSIDE3_EAST, BOTTOMSIDE4_EAST,
            HOLDER1_EAST, HOLDER2_EAST,
            SPINNER1_EAST, SPINNER2_EAST,
            BASIN1_EAST, BASIN2_EAST, BASIN3_EAST, BASIN4_EAST,
            HANDLE1_EAST, HANDLE2_EAST
    );

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public Gem_Fusion_Table(Properties pProperties) {
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
    public RenderShape getRenderShape(BlockState pState){
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof GemFusionEntity){
                ((GemFusionEntity) blockEntity).drops();
            }
        }

        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof GemFusionEntity) {
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (GemFusionEntity) entity, pPos);
            } else {
                throw new IllegalStateException("Container provider is missing.");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GemFusionEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()){
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.GEM_FUSION_ENTITY.get(), (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
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
