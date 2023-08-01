package dev.schmarni.schmacks;

import net.minecraft.client.gui.GuiGraphics;

/**
 * IHudModule
 */
public interface IHudModule {

	public void render(GuiGraphics graphics, float tickDelta);
}
