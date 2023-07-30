package dev.schmarni.schmacks.hacks;

import dev.schmarni.schmacks.IHack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;

/**
 * NoFallDamageThx
 */
public class NoFallDamageThx implements IHack {
	boolean active = false;
	private static final float GRAVITY_KICKBACK_OFFSET = 0.05f;

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
	}

	@Override
	public Text get_name() {
		return Text.literal("Long Fall Boots");
	}

	@Override
	public void slow_tick(int quater) {
	}


	@Override
	public void tick() {
		var mc = MinecraftClient.getInstance();
		// Disgusting Load Bearing Magic Number! without this you can just walk on the ceiling
		if (mc.player.getVelocity().y < -0.1f) {
			var pos = mc.player.getPos();
			mc.getNetworkHandler().sendPacket(
					new PlayerMoveC2SPacket.PositionAndOnGround(
							pos.x,
							pos.y + GRAVITY_KICKBACK_OFFSET,
							pos.z,
							false));


		}
	}

}
