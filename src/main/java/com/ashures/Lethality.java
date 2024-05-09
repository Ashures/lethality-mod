package com.ashures;

import com.ashures.item.ModItems;
import com.ashures.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lethality implements ModInitializer {
	public static final String MOD_ID = "lethality";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModSounds.registerModSounds();
	}
}