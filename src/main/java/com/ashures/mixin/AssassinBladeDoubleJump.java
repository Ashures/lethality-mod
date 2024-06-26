package com.ashures.mixin;

import com.ashures.item.ModItems;
import com.ashures.sound.ModSounds;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class AssassinBladeDoubleJump extends AbstractClientPlayerEntity {

    @Unique
    private int jumpCooldown = 5;
    @Unique
    private boolean jumpedLastFrame = false;
    @Shadow
    public Input input;

    @Shadow public abstract void sendMessage(Text message);

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    public AssassinBladeDoubleJump(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    private void tickMovement(CallbackInfo ci) {
        if (this.isOnGround() || this.isClimbing()) {
            jumpCooldown = 5;
        } else {
            jumpCooldown = Math.max(jumpCooldown - 1, 0);
        }

        if (input.jumping && this.getMainHandStack().isOf(ModItems.ASSASSIN_BLADE) && !jumpedLastFrame && jumpCooldown == 0
                && !this.isOnGround() && !this.getAbilities().flying && !this.isFallFlying() && !this.hasVehicle() && !this.isCreative()
        ) {
            Vec3d curVelocity = this.getVelocity();
            this.setVelocity(curVelocity.x, getJumpVelocity() * 2.0F, curVelocity.z);

            this.playSound(ModSounds.ASSASSIN_BLADE_DOUBLE_JUMP, 0.5F, 0.7F);

            jumpCooldown = 20;
        }

        jumpedLastFrame = input.jumping;
    }
}
