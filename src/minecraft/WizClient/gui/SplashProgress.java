package WizClient.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import WizClient.Palette;
import WizClient.util.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class SplashProgress {
	private static final int MAX = 7;
	private static int PROGRESS = 0;
	
	public static void update() {
		if(Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getResourceManager() == null) {
			return;
		}
		drawSplash(Minecraft.getMinecraft().getTextureManager());
	}
	
	public static void setProgress(int Progress, String Text) {
		PROGRESS = Progress;
		update();
	}
	
	public static void drawSplash(TextureManager tm) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		int scaleFactor = scaledResolution.getScaleFactor();
		
		Framebuffer frameBuffer = new Framebuffer(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor, true);
		frameBuffer.bindFramebuffer(false);
		
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.loadIdentity();
		GlStateManager.ortho(0.0D, (double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.loadIdentity();
		GlStateManager.translate(0.0F, 0.0F, -2000.9F);
		GlStateManager.disableFog();
		GlStateManager.disableDepth();
		GlStateManager.enableTexture2D();
		
		GlStateManager.resetColor();
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		
		int w = scaledResolution.getScaledWidth();
		int h = scaledResolution.getScaledHeight();
		
		Gui.drawRect(0, 0, w, h, Palette.SPLASH);
		
		tm.bindTexture(GuiMainMenu.minecraftTitleTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(w / 2 - 128, h / 8, 0, 0, 1024, 372, 256, 93, 1024f, 372f);
        
        int pw = MathHelper.floor_float(w / 1.8f);
        int px = w / 2 - pw / 2;
        int ph = MathHelper.floor_float(h / 1.5f);
        Gui.drawRelHollowRect(px, ph, pw, 10, 1, Palette.WHITE);
        int ppw = MathHelper.floor_double(((double) PROGRESS / MAX) * (w - pw));
        Gui.drawRelRect(px + 2, ph + 2, ppw - 2, 6, Palette.WHITE);
        
        int percent = (int) Math.floor((double) PROGRESS / MAX * 100);
        Gui.drawCenteredString(mc.fontRendererObj, percent + "%", w / 2, ph + 15, Palette.WHITE);
		
		frameBuffer.unbindFramebuffer();
		frameBuffer.framebufferRender(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor);
		
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		
		Minecraft.getMinecraft().updateDisplay();
		
	}
	
	public static void resetTextureState() {
		GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = -1;
	}
}












