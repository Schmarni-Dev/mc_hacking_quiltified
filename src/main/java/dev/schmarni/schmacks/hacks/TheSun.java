package dev.schmarni.schmacks.hacks;

import org.quiltmc.qsl.entity.effect.api.StatusEffectUtils;

import dev.schmarni.schmacks.IHack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;

/**
 * TheSun
 */
public class TheSun implements IHack {
	boolean active = false;

	@Override
	public boolean get_active() {
		return active;
	}

	@Override
	public void enable() {
		active = true;
	}

	@Override
	public void disable() {
		active = false;

		var mc = MinecraftClient.getInstance();
		mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
	}

	@Override
	public Text get_name() {
		return Text.literal("My Eyes!");
	}

	@Override
	public void slow_tick(int quater) {
		var mc = MinecraftClient.getInstance();
		mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, -1, 1, true, false));
	}

	@Override
	public void tick() {
	}

}
