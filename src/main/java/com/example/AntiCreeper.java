package com.example;


import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class AntiCreeper implements ModInitializer {
	private static Config config;

    public static final Logger LOGGER = LoggerFactory.getLogger("anti-creeper");


	@Override
	public void onInitialize() {
		config = new Config(FabricLoader.getInstance().getConfigDir().resolve("anti-creeper.properties"));
		try {
			config.initialize();
		} catch (IOException e) {
			LOGGER.error("Failed to initialize Anti Creeper configuration, default values will be used instead");
			LOGGER.error("", e);
		}


		CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("anticreeper").then(CommandManager.literal("load").executes(context -> {
				try {
					config.initialize();
				} catch (IOException e) {
					try {
						config.save();
					} catch (IOException ignored) {}

					return -1;
				}

				return 1;
			})).then(CommandManager.literal("setDestroy").then(CommandManager.argument("value", IntegerArgumentType.integer()).executes(context -> {
				final boolean value = !(0 == IntegerArgumentType.getInteger(context, "value"));

				config.setDestroy(value);

				try {
					config.save();
				} catch (IOException e) {
					try {
						config.load();
					} catch (IOException ignored) {}

					return -1;
				}

				return 0;
			}))).then(CommandManager.literal("setProtected").then(CommandManager.argument("value", IntegerArgumentType.integer()).executes(context -> {
				final boolean value = !(0 == IntegerArgumentType.getInteger(context, "value"));

				config.setProtectAreas(value);

				try {
					config.save();
				} catch (IOException e) {
					try {
						config.load();
					} catch (IOException ignored) {}

					return -1;
				}

				return 0;
			}))));
		}));
	}

	public static Config getConfig() {
		return config;
	}
}