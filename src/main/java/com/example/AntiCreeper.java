package com.example;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class AntiCreeper implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	private static Config config;

    public static final Logger LOGGER = LoggerFactory.getLogger("anti-creeper");


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		config = new Config(FabricLoader.getInstance().getConfigDir().resolve("anti-creeper.properties"));
		try {
			config.initialize();
		} catch (IOException e) {
			LOGGER.error("Failed to initialize Anti Creeper configuration, default values will be used instead");
			LOGGER.error("", e);
		}
	}

	public static Config getConfig() {
		return config;
	}
}