/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;

public class MoCBlockLeaf extends LeavesBlock {
    private static final int MAX_DISTANCE = 6;  // Maximum distance from log before decay

    public MoCBlockLeaf(BlockBehaviour.Properties properties) {
        super(properties
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isSuffocating((s, g, p) -> false)
                .isViewBlocking((s, g, p) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, MAX_DISTANCE).setValue(PERSISTENT, Boolean.FALSE));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        int distance = MAX_DISTANCE;
        boolean foundLog = false;

        // Check all directions for logs or connected leaves
        for (Direction dir : Direction.values()) {
            BlockState adjacent = level.getBlockState(pos.relative(dir));
            
            // Direct connection to log
            if (adjacent.getBlock() == MoCBlocks.wyvwoodLog.get() || adjacent.is(BlockTags.LOGS)) {
                return state.setValue(PERSISTENT, true).setValue(DISTANCE, 1);
            }
            
            // Connected to persistent leaf
            if (adjacent.getBlock() instanceof LeavesBlock) {
                if (adjacent.getValue(PERSISTENT)) {
                    return state.setValue(PERSISTENT, true).setValue(DISTANCE, 1);
                }
                distance = Math.min(distance, adjacent.getValue(DISTANCE) + 1);
            }
        }

        // If we found a valid leaf chain, update distance
        if (distance < MAX_DISTANCE) {
            return state.setValue(DISTANCE, distance);
        }

        // Schedule decay check
        level.scheduleTick(pos, this, 1);
        return state;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (state.getValue(PERSISTENT)) {
            return;
        }

        // Check if we're too far from any log
        int currentDistance = state.getValue(DISTANCE);
        if (currentDistance >= MAX_DISTANCE) {
            // Decay the leaf
            level.removeBlock(pos, false);
            return;
        }

        // Update distance from logs
        BlockState newState = updateDistance(state, level, pos);
        if (newState != state) {
            level.setBlock(pos, newState, Block.UPDATE_ALL);
        }
    }

    private BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
        int distance = MAX_DISTANCE;

        // Check all directions
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.getBlock() == MoCBlocks.wyvwoodLog.get() || neighborState.is(BlockTags.LOGS)) {
                return state.setValue(PERSISTENT, true).setValue(DISTANCE, 1);
            }

            if (neighborState.getBlock() instanceof LeavesBlock) {
                if (neighborState.getValue(PERSISTENT)) {
                    return state.setValue(PERSISTENT, true).setValue(DISTANCE, 1);
                }
                distance = Math.min(distance, neighborState.getValue(DISTANCE) + 1);
            }
        }

        return state.setValue(DISTANCE, distance);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }
}
