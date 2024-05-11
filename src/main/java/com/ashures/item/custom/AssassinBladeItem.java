package com.ashures.item.custom;

import com.ashures.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AssassinBladeItem extends SwordItem {
    private final int cooldown;

    public AssassinBladeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, int cooldown, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.cooldown = cooldown;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        super.use(world, user, hand);

        ItemStack itemStack = user.getStackInHand(hand);

        if (world.isClient() && !user.isFallFlying()) {
            user.getItemCooldownManager().set(this, this.cooldown);

            Boost(user);
            user.playSound(ModSounds.ASSASSIN_BLADE_DASH, SoundCategory.PLAYERS, 0.5F, 0.6F);

            return TypedActionResult.success(itemStack, true);
        }

        return TypedActionResult.fail(itemStack);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.isDead()) {
            StatusEffectInstance entitySpeedEffect = attacker.getStatusEffect(StatusEffects.SPEED);

            int speedAmplifier = entitySpeedEffect == null ? 0 : Math.min(1, entitySpeedEffect.getAmplifier() + 1);
            attacker.setStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, speedAmplifier), null);
        }

        return super.postHit(stack, target, attacker);
    }

    private void Boost(PlayerEntity playerEntity) {
        float boostLevel = 1.0f;

        float f = playerEntity.getYaw();
        float g = playerEntity.getPitch();
        float h = -MathHelper.sin(f * ((float)Math.PI / 180)) * MathHelper.cos(g * ((float)Math.PI / 180));
        float k = -MathHelper.sin(g * ((float)Math.PI / 180));
        float l = MathHelper.cos(f * ((float)Math.PI / 180)) * MathHelper.cos(g * ((float)Math.PI / 180));
        float m = MathHelper.sqrt(h * h + k * k + l * l);
        float n = 3.0f * ((1.0f + boostLevel) / 4.0f);
        playerEntity.addVelocity(h *= n / m, 0, l *= n / m);
        playerEntity.useRiptide(20);
        if (playerEntity.isOnGround()) {
            float o = 1.1999999f;
            playerEntity.move(MovementType.SELF, new Vec3d(0.0, o, 0.0));
        }
    }
}
