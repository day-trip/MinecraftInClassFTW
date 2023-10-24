package WizClient;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;

public class ScreenTripper {
	protected static Minecraft mc;
	protected static Framebuffer framebuffer;
	
	public static void init(Minecraft mc) {
		ScreenTripper.mc = mc;
		framebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false);
        framebuffer.setFramebufferFilter(9728);
	}
	
	public static void tripScreen(GuiScreen screen) {
		ScaledResolution scaledresolution = new ScaledResolution(mc);
        int j = scaledresolution.getScaleFactor();
        int k = scaledresolution.getScaledWidth();
        int l = scaledresolution.getScaledHeight();

        if (OpenGlHelper.isFramebufferEnabled())
        {
            framebuffer.framebufferClear();
        }
        else
        {
            GlStateManager.clear(256);
        }

        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -200.0F);

        if (!OpenGlHelper.isFramebufferEnabled())
        {
            GlStateManager.clear(16640);
        }
        
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        
		screen.setWorldAndResolution(mc, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
		screen.updateScreen();
		screen.drawScreen(Mouse.getX(), Mouse.getY(), 0);
		
		framebuffer.unbindFramebuffer();

        if (OpenGlHelper.isFramebufferEnabled())
        {
            framebuffer.framebufferRender(k * j, l * j);
        }

        mc.updateDisplay();

        try
        {
            Thread.yield();
        }
        catch (Exception var16)
        {
            ;
        }
	}
}
