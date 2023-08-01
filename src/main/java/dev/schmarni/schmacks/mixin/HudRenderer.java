package dev.schmarni.schmacks.mixin;

import java.util.HashSet;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import dev.schmarni.schmacks.IHudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.InGameHud;

/**
 * HudRenderer
 */
@Mixin(InGameHud.class)
public class HudRenderer {
	public static HashSet<IHudModule> modules;

	@Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;F)V", at = @At("TAIL"))
	public void render(GuiGraphics graphics, float tickDelta) {
		if (!MinecraftClient.isHudEnabled())
			return;
		for (IHudModule module : modules) {
			module.render(graphics, tickDelta);

		}
	}

}
