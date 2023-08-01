package dev.schmarni.schmacks.hacks;

import dev.schmarni.schmacks.IHack;
import dev.schmarni.schmacks.Schmacks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;

/**
 * Flight
 */
public class Flight implements IHack {
	boolean active = false;
	private static final float ANTI_ANTI_FLY_KICK_OFFSET = 0.05f;

	@Override
	public void slow_tick(int quaters) {
		var mc = MinecraftClient.getInstance();
		mc.player.getAbilities().allowFlying = true;
		if (quaters == 3 && MinecraftClient.getInstance().player.getAbilities().flying)
			server_no_kick_pls();
	}

	// TODO: Fix knockback yeeting you out of the sky violently
	private void server_no_kick_pls() {
		var mc = MinecraftClient.getInstance();
		if (mc.player.isOnGround())
			return;
		var pos = mc.player.getPos();
		mc.getNetworkHandler().sendPacket(
				new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, pos.y - ANTI_ANTI_FLY_KICK_OFFSET, pos.z,
						false));
		// Schmacks.run_next_tick(() -> {
		// var mc_ = MinecraftClient.getInstance();
		// var pos_ = mc_.player.getPos();
		// mc.getNetworkHandler().sendPacket(
		// new PlayerMoveC2SPacket.PositionAndOnGround(pos_.x, pos_.y +
		// ANTI_ANTI_FLY_KICK_OFFSET, pos_.z,
		// false));
		// });
	}

	@Override
	public void tick() {

	}

	@Override
	public boolean get_active() {
		return active;
	}

	@Override
	public void enable() {
		active = true;
		var mc = MinecraftClient.getInstance();
		mc.player.getAbilities().allowFlying = true;
		server_no_kick_pls();
	}

	@Override
	public void disable() {
		active = false;
		var mc = MinecraftClient.getInstance();
		mc.getNetworkHandler().getPlayerListEntry(mc.player.getGameProfile().getId()).getGameMode()
				.setAbilities(mc.player.getAbilities());
		server_no_kick_pls();
	}

	@Override
	public Text get_name() {
		return Text.literal("Flight");
	}

}
