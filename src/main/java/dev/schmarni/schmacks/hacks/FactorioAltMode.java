package dev.schmarni.schmacks.hacks;

import javax.swing.OverlayLayout;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4d;
import org.joml.Vector4f;

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
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.DisplayEntityRenderer.ItemDisplayEntityRenderer;
import net.minecraft.client.render.entity.DisplayEntityRenderer.TextDisplayEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.DisplayEntity.ItemDisplayEntity;
import net.minecraft.entity.decoration.DisplayEntity.TextDisplayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

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

	// private void render_overlay(WorldRenderContext ctx) {
	// if (!active)
	// return;
	// var mc = MinecraftClient.getInstance();
	//
	// var vcp = ctx.consumers();
	// var vc = vcp.getBuffer(RenderLayer.getGuiOverlay());
	// var mat_stack = ctx.matrixStack();
	// // mat.rotate((float) Math.PI, 0.0F, 1.0F, 0.0F);
	// // mat.scale(-0.025F, -0.025F, -0.025F);
	// var pos = ctx.gameRenderer().getCamera().getPos();
	// mat_stack.translate(-pos.x, -pos.y, -pos.z);
	// var mat = mat_stack.peek().getModel();
	//
	// vc.vertex(mat, 0f, 0f, 0f).color(0x0fff00ff).next();
	// vc.vertex(mat, 0f, 1f, 0f).color(0x0fff00ff).next();
	// vc.vertex(mat, 1f, 1f, 0f).color(0x0fff00ff).next();
	// vc.vertex(mat, 1f, 0f, 0f).color(0x0fff00ff).next();
	//
	// mat.translate(new Vector3f((float) pos.x, (float) pos.y, (float) pos.z));
	// mc.getItemRenderer().renderItem(Items.GRASS_BLOCK.getDefaultStack(),
	// ModelTransformationMode.GUI, false,
	// mat_stack, vcp, 15, OverlayTexture.DEFAULT_UV,
	// mc.getItemRenderer().getModels().getModel(Items.GRASS_BLOCK));
	// }
	private void render_overlay(WorldRenderContext ctx) {
		if (!active)
			return;

		var mc = MinecraftClient.getInstance();
		var vcp = ctx.consumers();
		var vc = vcp.getBuffer(RenderLayer.getGuiOverlay());
		var mat_stack = ctx.matrixStack();

		var pos = ctx.gameRenderer().getCamera().getPos();
		mat_stack.push();
		mat_stack.translate(-pos.x, -pos.y, -pos.z);
		var mat = mat_stack.peek().getModel();

		vc.vertex(mat, 0f, 0f, 0f).color(0xffffffff).next();
		vc.vertex(mat, 0f, 1f, 0f).color(0xffffffff).next();
		vc.vertex(mat, 1f, 1f, 0f).color(0xffffffff).next();
		vc.vertex(mat, 1f, 0f, 0f).color(0xffffffff).next();

		mat_stack.translate(0.5, 0.5, 0.5);

		ItemStack grassBlockStack = new ItemStack(Items.GRASS_BLOCK);
		mc.getItemRenderer().renderItem(grassBlockStack, ModelTransformationMode.FIXED, false,
				mat_stack, vcp, 0, OverlayTexture.DEFAULT_UV,
				mc.getItemRenderer().getModels().getModel(Items.GRASS_BLOCK));
		mat_stack.translate(pos.x, pos.y, pos.z);
		mat_stack.pop();
	}

	@Override
	public void init() {

		WorldRenderEvents.LAST.register(this::render_overlay);
		Schmacks.LOGGER.info("Vision Pro");
		// HudManager.register(new VisionScreen(new Identifier("wdasdwdsadwfgjjl",
		// "odaiuuzhflkueuhle")));
	}

	// private class VisionScreen extends Hud {
	// // static final Identifier TEXT_IDENT = new Identifier("odifuhpsaoh",
	// // "dasdwadewgsfd");
	//
	// public VisionScreen(@NotNull Identifier id) {
	// super(id);
	// this.setEnabled(true);
	// // this.components.add(new TextHudComponent(TEXT_IDENT, 0, 0,
	// // Text.literal("T")));
	// Schmacks.LOGGER.info("Vision Pro");
	// }
	//
	// @Override
	// public void render(GuiGraphics graphics, float tickDelta) {
	// super.render(graphics, tickDelta);
	// var pos = get_screen_pos_from_world_pos(Vec3d.ofCenter(Vec3i.ZERO));
	// var mc = MinecraftClient.getInstance();
	// graphics.drawText(mc.textRenderer, "X", pos.x, pos.y, 0xffffff, true);
	// if (pos.distance(0, 0) == 0)
	// return;
	// Schmacks.LOGGER.info("render: " + String.valueOf(pos.x) + ", " +
	// String.valueOf(pos.y));
	// }
	//
	// }
	//
	// //
	// // private static Vector2i get_screen_pos_from_world_pos(Vec3d pos) {
	// // var vec4 = new Vector4d(pos.x, pos.y, pos.z, 1d);
	// // var final_matrix =
	// // RenderSystem.getProjectionMatrix().mul(RenderSystem.getModelViewMatrix());
	// // var done = vec4.mul(final_matrix);
	// // var out = new Vector2d(done.x / done.w, done.y / done.w);
	// // var window = MinecraftClient.getInstance().getWindow();
	// // return new Vector2i((int) out.x * window.getScaledWidth(), (int) out.y *
	// // window.getScaledHeight());
	// //
	// // }
	// private static Vector3d fix_vector(Vec3d vec) {
	// return new Vector3d(vec.x, vec.y, vec.z);
	// }
	//
	// private static Vector2i get_screen_pos_from_world_pos(Vec3d pos) {
	// var mc = MinecraftClient.getInstance();
	// Camera camera = mc.gameRenderer.getCamera();
	// var vec4 = new Vector4f((float) (pos.x - camera.getPos().x), (float) (pos.y -
	// camera.getPos().y),
	// (float) (pos.z - camera.getPos().z), 1.0f);
	//
	// Matrix4f viewMatrix = new Matrix4f();
	// viewMatrix.identity();
	// viewMatrix.translate((float) -camera.getPos().x, (float) -camera.getPos().y,
	// (float) -camera.getPos().z);
	// viewMatrix.rotate((float) Math.toRadians(camera.getYaw() + 180.0), 0.0f,
	// 1.0f, 0.0f);
	// viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), 1.0f, 0.0f,
	// 0.0f);
	//
	// vec4.mul(viewMatrix);
	//
	// Matrix4f projMatrix =
	// mc.gameRenderer.getBasicProjectionMatrix(mc.options.getFov().get());
	// vec4.mul(projMatrix);
	// Schmacks.LOGGER.info("Vec4: " + vec4.toString());
	//
	// double screenWidth = (double)
	// MinecraftClient.getInstance().getWindow().getScaledWidth();
	// double screenHeight = (double)
	// MinecraftClient.getInstance().getWindow().getScaledHeight();
	//
	// double xScreen = (vec4.x + 1.0) * 0.5 * screenWidth;
	// double yScreen = (1.0 - vec4.y) * 0.5 * screenHeight;
	//
	// int pixelX = (int) Math.round(xScreen);
	// int pixelY = (int) Math.round(yScreen);
	//
	// return new Vector2i(pixelX, pixelY);
	// }

}
