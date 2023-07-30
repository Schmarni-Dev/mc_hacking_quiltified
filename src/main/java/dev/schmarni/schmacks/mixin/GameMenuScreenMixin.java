package dev.schmarni.schmacks.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.schmarni.schmacks.Schmacks;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
	protected GameMenuScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "init", at = @At("HEAD"))
	public void onInit(CallbackInfo ci) {
		this.addDrawableChild(ButtonWidget.builder(Text.literal("HIIIIII"),(btn)-> {
			Schmacks.open_schmacks_screen();
			}).position(10,10).build());
	}
}
