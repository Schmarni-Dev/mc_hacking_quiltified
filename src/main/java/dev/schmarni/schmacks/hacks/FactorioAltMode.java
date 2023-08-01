package dev.schmarni.schmacks.hacks;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4d;

import dev.lambdaurora.spruceui.hud.Hud;
import dev.lambdaurora.spruceui.hud.HudManager;
import dev.lambdaurora.spruceui.hud.component.TextHudComponent;
import dev.schmarni.schmacks.IHack;
import dev.schmarni.schmacks.Schmacks;
import dev.schmarni.schmacks.util.Vec2i;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Camera;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexBuffer;

// For maybe making the holo lib better!
/**
 *private static void renderInWallOverlay(Sprite sprite, MatrixStack matrices) {
		RenderSystem.setShaderTexture(0, sprite.getId());
		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBufferBuilder();
		float f = 0.1F;
		float g = -1.0F;
		float h = 1.0F;
		float i = -1.0F;
		float j = 1.0F;
		float k = -0.5F;
		float l = sprite.getMinU();
		float m = sprite.getMaxU();
		float n = sprite.getMinV();
		float o = sprite.getMaxV();
		Matrix4f matrix4f = matrices.peek().getModel();
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
		bufferBuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv(m, o).next();
		bufferBuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv(l, o).next();
		bufferBuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv(l, n).next();
		bufferBuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv(m, n).next();
		BufferRenderer.drawWithShader(bufferBuilder.end());
	}
 */

/**
 * FactorioAltMode
 */
public class FactorioAltMode implements IHack {
	boolean active = false;
	VisionScreen screen;

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
		return Text.literal("The Third Eye");
	}

	@Override
	public void slow_tick(int quater) {
	}

	@Override
	public void tick() {
	}

	@Override
	public void init() {

		Schmacks.LOGGER.info("Vision Pro");
		HudManager.register(new VisionScreen(new Identifier("wdasdwdsadwfgjjl", "odaiuuzhflkueuhle")));
	}

	private class VisionScreen extends Hud {
		// static final Identifier TEXT_IDENT = new Identifier("odifuhpsaoh",
		// "dasdwadewgsfd");

		public VisionScreen(@NotNull Identifier id) {
			super(id);
			this.setEnabled(true);
			// this.components.add(new TextHudComponent(TEXT_IDENT, 0, 0,
			// Text.literal("T")));
			Schmacks.LOGGER.info("Vision Pro");
		}

		@Override
		public void render(GuiGraphics graphics, float tickDelta) {
			super.render(graphics, tickDelta);
			var pos = get_screen_pos_from_world_pos(Vec3d.ofCenter(Vec3i.ZERO));
			var mc = MinecraftClient.getInstance();
			graphics.drawText(mc.textRenderer, "X", pos.x, pos.y, 0xffffff, true);
			if (pos.distance(0, 0) == 0)
				return;
			Schmacks.LOGGER.info("render: " + String.valueOf(pos.x) + ", " +
					String.valueOf(pos.y));
		}

	}

	//
	// private static Vector2i get_screen_pos_from_world_pos(Vec3d pos) {
	// var vec4 = new Vector4d(pos.x, pos.y, pos.z, 1d);
	// var final_matrix =
	// RenderSystem.getProjectionMatrix().mul(RenderSystem.getModelViewMatrix());
	// var done = vec4.mul(final_matrix);
	// var out = new Vector2d(done.x / done.w, done.y / done.w);
	// var window = MinecraftClient.getInstance().getWindow();
	// return new Vector2i((int) out.x * window.getScaledWidth(), (int) out.y *
	// window.getScaledHeight());
	//
	// }
	private static Vector3d fix_vector(Vec3d vec) {
		return new Vector3d(vec.x,vec.y,vec.z);
	}
	private static Vector2i get_screen_pos_from_world_pos(Vec3d pos) {
		var vec4 = new Vector4d(pos.x, pos.y, pos.z, 1d);
		var mc = MinecraftClient.getInstance();
		Camera camera = mc.gameRenderer.getCamera();

		Matrix4d viewMatrix = new Matrix4d();
		viewMatrix = viewMatrix.identity();
		viewMatrix.translate(fix_vector(camera.getPos().negate()));
		viewMatrix.rotate(camera.getPitch(), new Vector3f(1.0f, 0.0f, 0.0f));
		viewMatrix.rotate(camera.getYaw() + 180.0f, new Vector3f(0.0f, 1.0f, 0.0f));

		vec4.mul(viewMatrix);
		vec4.mul(RenderSystem.getProjectionMatrix());
		Schmacks.LOGGER.info(vec4.toString());
		double screenWidth = (float) MinecraftClient.getInstance().getWindow().getFramebufferWidth();
		double screenHeight = (float) MinecraftClient.getInstance().getWindow().getFramebufferHeight();

		double xScreen = (vec4.x() + 1.0d) * 0.5d * screenWidth;
		double yScreen = (1.0d - vec4.y()) * 0.5d * screenHeight;

		int pixelX = (int) xScreen;
		int pixelY = (int) yScreen;

		return new Vector2i(pixelX, pixelY);

	}

}
