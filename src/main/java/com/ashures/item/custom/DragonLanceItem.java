package com.ashures.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DragonLanceItem extends SwordItem {
    private final int cooldown;

    public DragonLanceItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, int cooldown, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

        this.cooldown = cooldown;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient()) {
            user.getItemCooldownManager().set(this, this.cooldown);

            world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.PLAYERS, 1.2f, 0.6f);

            LivingEntity target = user.getAttacker();

            for (int i = 0; i < 3; i++) {
                EndermanEntity enderman = new EndermanEntity(EntityType.ENDERMAN, world);
                enderman.setPosition(user.getPos());
                enderman.setHealth(1);
                enderman.setTarget(target);
                enderman.setSilent(true);

                world.spawnEntity(enderman);
            }
        }

        return TypedActionResult.success(itemStack);
    }
}
