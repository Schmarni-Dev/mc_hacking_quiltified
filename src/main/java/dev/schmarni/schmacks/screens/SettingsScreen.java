package dev.schmarni.schmacks.screens;

import java.util.ArrayList;
import java.util.stream.IntStream;

import dev.schmarni.schmacks.IScreenOption;
import dev.schmarni.schmacks.Schmacks;
import dev.schmarni.schmacks.util.Vec2i;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.component.TextComponent;
import net.minecraft.client.gui.screen.option.KeyBindsScreen;

import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;

/**
 * SettingsScreen
 */
public class SettingsScreen extends Screen {

	private IScreenOption[] options;
	private int buttons_per_colum;
	private static final int BUTTON_WIDTH = (int) (ButtonWidget.DEFAULT_WIDTH);

	public Vec2i get_position_from_index(int index) {
		var vec = new Vec2i();
		vec.y =(int)(ButtonWidget.DEFAULT_HEIGHT * 0.25) + ( ((index) % buttons_per_colum) * (int) (ButtonWidget.DEFAULT_HEIGHT * 1.5));

		vec.x = (int) ((ButtonWidget.DEFAULT_HEIGHT * 0.25) + (Math.floorDiv(index, buttons_per_colum) * BUTTON_WIDTH * 1.1));
		return vec;
	}

	public SettingsScreen(IScreenOption[] options) {
		super(Text.literal("Schmacks!"));
		this.options = options;
	}

	@Override
	protected void init() {
		buttons_per_colum = (int) (getArea().height() / (ButtonWidget.DEFAULT_HEIGHT * 1.5));
		Schmacks.LOGGER.info("buttons_per_colum" + String.valueOf(buttons_per_colum));
		Schmacks.LOGGER.info("heigth" + String.valueOf(getArea().height()));
		var buttons = new ButtonWidget[options.length];
		for (int i = 0; i < options.length; i++) {
			buttons[i] = make_button(options[i], i);
			addDrawableChild(buttons[i]);
			Schmacks.LOGGER.info(String.valueOf(i));
		}

	}

	private ButtonWidget make_button(IScreenOption option, int index) {
		var position = get_position_from_index(index);
		ButtonWidget button = ButtonWidget.builder(button_msg(option), (btn) -> {
			option.set_active(!option.get_active());
			btn.setMessage(button_msg(option));
		}).position(position.x, position.y).width(BUTTON_WIDTH).build();
		return button;
	}

	private static Text button_msg(IScreenOption option) {
		return MutableText.create(option.get_name().asComponent()).append(": ")
				.append(get_enabled_text(option.get_active()));
	}

	private static Text get_enabled_text(boolean is_enabled) {
		return Text.literal(is_enabled ? "Enabled" : "Disabled");
	}

}
