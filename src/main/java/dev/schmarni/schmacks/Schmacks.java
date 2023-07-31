package dev.schmarni.schmacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.schmarni.schmacks.hacks.Flight;
import dev.schmarni.schmacks.hacks.NoFallDamageThx;
import dev.schmarni.schmacks.screens.SettingsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class Schmacks implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Schmacks");
	// private static final IHack[] HACKS = { new Flight(), new NoFallDamageThx() };
	private static List<IHack> HACKS = new LinkedList<>();
	private static long TICK_COUNTER = 0;
	private static List<Runnable> RUN_NEXT_TICK = new LinkedList<>();

	private static void initialize_base_modules() {
		HACKS.add(new Flight());
		HACKS.add(new NoFallDamageThx());
	}
	@Override
	public void onInitializeClient(ModContainer mod) {
		// initialize_base_modules();
		// ClientTickEvents.END.register((client) -> {
		// 	if (client.player == null)
		// 		return;
		// 	TICK_COUNTER++;
		// 	for (Runnable task : RUN_NEXT_TICK) {
		// 		task.run();
		// 	}
		// 	RUN_NEXT_TICK.clear();
		// 	for (var hack : HACKS) {
		// 		run_hack(hack);
		// 	}
		// });
	}

	private void run_hack(IHack hack) {
		if (!hack.get_active())
			return;
		if (TICK_COUNTER % 5 == 0) {
			hack.slow_tick((int) ((TICK_COUNTER % 20) / 5));
		}
		hack.tick();
	}

	public static void open_schmacks_screen() {
		MinecraftClient.getInstance().setScreen(new SettingsScreen(HACKS));
	}

	public static void run_next_tick(Runnable task) {
		RUN_NEXT_TICK.add(task);
	}

	public static IHack[] get_module_list_clone() {
		return (IHack[])HACKS.toArray();
	}
	public static void add_module(IHack module){
		HACKS.add(module);
	}

}
