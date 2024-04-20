package com.nikotokiller.enigmamagica.item.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.slf4j.Logger;

public class DesertStaffItem extends Item {

    public DesertStaffItem(Properties pProperties) {
        super(pProperties);
    }

    private static final int SEARCH_RADIUS = 5000;

    public static BlockPos nearestDesertBiomePos = null;

    public static SoundEvent soundEvent = SoundEvents.AMETHYST_BLOCK_BREAK;

    public static int check2 = 0;

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!(pPlayer instanceof Player)) {
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        }

        Player player = (Player) pPlayer;
        Level world = player.getCommandSenderWorld();

        if (world == null || player.getMainHandItem().isEmpty()) {
            return InteractionResultHolder.fail(player.getItemInHand(pUsedHand));
        }

        if (player.getMainHandItem().getItem() instanceof DesertStaffItem && check2 == 1) {
            check2++;

                player.sendSystemMessage(Component.literal("Searching for desert"));

            BlockPos playerPos = player.blockPosition();
            int minX = playerPos.getX() - SEARCH_RADIUS;
            int minZ = playerPos.getZ() - SEARCH_RADIUS;
            int maxX = playerPos.getX() + SEARCH_RADIUS;
            int maxZ = playerPos.getZ() + SEARCH_RADIUS;

            double nearestDistanceSq = Double.MAX_VALUE;

            for (int x = minX; x <= maxX; x += 128) {
                for (int z = minZ; z <= maxZ; z += 128) {
                    BlockPos pos = new BlockPos(x, playerPos.getY(), z);
                    Holder<Biome> biomeResourceKey = world.getBiome(pos);
                    //I know it is disgusting
                    if (biomeResourceKey.toString().contains("minecraft:desert")) {
                        double distanceSq = player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
                        if (distanceSq < nearestDistanceSq) {
                            nearestDesertBiomePos = pos;
                            nearestDistanceSq = distanceSq;
                        }
                    }
                }
            }

            if (nearestDesertBiomePos != null) {
                player.sendSystemMessage(Component.literal("Desert found!"));
            } else {
                player.sendSystemMessage(Component.literal("Failed to find Desert"));
            }

            check2 = 0;
            return InteractionResultHolder.success(player.getMainHandItem());
        }
        check2++;
        return InteractionResultHolder.success(player.getMainHandItem());
    }



}
