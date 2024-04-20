package com.nikotokiller.enigmamagica.event;

import com.nikotokiller.enigmamagica.EnigmaMagica;
import com.nikotokiller.enigmamagica.item.custom.DesertStaffItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EnigmaMagica.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void OnEntityDamage(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            ItemStack weapon = player.getMainHandItem();

            if (!weapon.isEmpty() && weapon.getItem() instanceof DesertStaffItem) {
                LivingEntity target = event.getEntity();
                target.setPos(DesertStaffItem.nearestDesertBiomePos.getX(), DesertStaffItem.nearestDesertBiomePos.getY(), DesertStaffItem.nearestDesertBiomePos.getZ());
            }
        }
    }

}
