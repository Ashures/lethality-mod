package com.ashures.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DragonLanceItem extends SwordItem {
    private int cooldown;

    public DragonLanceItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, int cooldown, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

        this.cooldown = cooldown;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient()) {
            user.getItemCooldownManager().set(this, this.cooldown);

            world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.PLAYERS, 0.8f, 0.5f);

            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightning.setPosition(user.getBlockPos().toCenterPos());
            lightning.setCosmetic(true);

            for (int i = 0; i < 10; i++) {
                world.spawnEntity(lightning);
            }
        }

        return TypedActionResult.success(itemStack);
    }
}
