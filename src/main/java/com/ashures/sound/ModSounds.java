package com.ashures.sound;

import com.ashures.Lethality;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent ASSASSIN_BLADE_DASH = registerSound("assassin_blade_dash");

    private static SoundEvent registerSound(String name) {
        Identifier id = new Identifier(Lethality.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        Lethality.LOGGER.info("Registering Mod Sounds for " + Lethality.MOD_ID);
    }
}
