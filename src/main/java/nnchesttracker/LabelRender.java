package nnchesttracker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import nnchesttracker.core.Configs;
import nnchesttracker.core.Configs.RenderLabelType;

public class LabelRender {

	public static void drawLabel(RenderManager rm, FontRenderer fontRendererIn, String str, BlockPos pos, ItemStack item) {
		float x = (float) (pos.getX() + 0.5 - rm.viewerPosX);
		float y = (float) (pos.getY() + 1.2 - rm.viewerPosY);
		float z = (float) (pos.getZ() + 0.5 - rm.viewerPosZ);
		int verticalShift = 0;
		float viewerYaw = rm.playerViewY;
		float viewerPitch = rm.playerViewX;
		boolean isThirdPersonFrontal = rm.options.thirdPersonView == 2;
		boolean isSneaking = false;

		if (Configs.renderType == RenderLabelType.CUSTOM) {
			drawNameplate(fontRendererIn, str, x, y, z, verticalShift, viewerYaw, viewerPitch, isThirdPersonFrontal, isSneaking);

			if (!item.isEmpty()) {
				renderItem(item, x, y, z);
			}
		} else {
			EntityRenderer.drawNameplate(fontRendererIn, str, x, y, z, verticalShift, viewerYaw, viewerPitch, isThirdPersonFrontal, isSneaking);
		}
	}

	/*
	 * public static void renderItem(RenderManager rm, ItemStack item, BlockPos pos)
	 * { float x = (float) (pos.getX() + 0.5 - rm.viewerPosX); float y = (float)
	 * (pos.getY() + 1.2 - rm.viewerPosY); float z = (float) (pos.getZ() + 0.5 -
	 * rm.viewerPosZ); }
	 */
	public static void renderItem(ItemStack item, float x, float y, float z) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		Minecraft.getMinecraft().getRenderItem().renderItem(item, TransformType.GROUND);
		GlStateManager.disableLighting();
		GlStateManager.popMatrix();
	}

	public static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-0.025F, -0.025F, 0.025F);
		// GlStateManager.disableLighting();
		GlStateManager.depthMask(false);

		GlStateManager.disableDepth();

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		int i = fontRendererIn.getStringWidth(str) / 2;
		GlStateManager.disableTexture2D();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(-i - 1, -1 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		bufferbuilder.pos(-i - 1, 8 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		bufferbuilder.pos(i + 1, 8 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		bufferbuilder.pos(i + 1, -1 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();

		fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, -1);
		GlStateManager.enableDepth();

		GlStateManager.depthMask(true);
		// fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2,
		// verticalShift, isSneaking ? 553648127 : -1);
		// GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
}
