package net.minecraft.client.gui;


import WizClient.Client;
import WizClient.EntityPlayerMock;
import WizClient.GuiBedrockPlay;
import WizClient.LoadingPopup;
import WizClient.Palette;
import WizClient.ui.auth.SessionChanger;
import day.trippin.GuiLoadingPopup;

import java.io.IOException;
import java.net.URI;
import java.util.Random;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.CustomPanorama;
import net.optifine.CustomPanoramaProperties;
import net.optifine.reflect.Reflector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import com.mojang.authlib.GameProfile;


public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
    private static final Logger logger = LogManager.getLogger();

    private String splashText = "missingno";
    
    protected final GuiLoadingPopup loadingPopup;

    public GuiMainMenu()
    {
    	this.loadingPopup = new GuiLoadingPopup(width / 2, height / 2);
    	loadingPopup.visible = false;
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
    	super.initGui();
    	Client.getInstance().getDiscordRP().update("Idle", "Main menu");

        int i = 24;
        int j = this.height / 4 + 40;

        this.buttonList.add(new GuiButton(0, this.width / 2 - 77, j, 154, 32, "Play"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 77, j + 34, 154, 32, "New & Featured"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 77, j + 68, 154, 32, "Options"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 77, j + 102, 154, 32, "Manage Account"));

        this.mc.setConnectedToRealms(false);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     * @throws  
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 0)
        {
        	this.mc.displayGuiScreen(new GuiBedrockPlay(this));
        }
        
        if (button.id == 2) {
        	// loadingPopup.visible = true;
        	SessionChanger.getInstance().setUserMicrosoft(() -> {
        		// loadingPopup.visible = false;
        		return null;
        	});
        }
        
        if (button.id == 3) {
        	try
            {
                Class<?> oclass = Class.forName("java.awt.Desktop");
                Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI("https://f.tbot.run/")});
            }
            catch (Throwable throwable)
            {
                logger.error("Couldn't rickroll you :(", throwable);
            }
        }
    }
   
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	this.drawPanoramaBackground(partialTicks);
    	
    	// Mock
    	// EntityPlayerMock ent = new EntityPlayerMock(mc, new GameProfile(UUID.randomUUID(), "GIRI_J"));
    	// ent.onUpdate();
        // GuiInventory.drawEntityOnScreen(0, 0, 30, mouseX, mouseY, ent);
    	
    	// Logo
        this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(width / 2 - 96, height / 15, 0, 0, 1024, 372, 192, 70, 1024f, 372f);
        
        // Splash
        this.splashText = "Get smarter!";
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
        float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
        f = f * 100.0F / (float)(this.fontRendererObj.getStringWidth(this.splashText) + 32);
        GlStateManager.scale(f, f, f);
        Gui.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -256);
        GlStateManager.popMatrix();
        
        // Metadata
        Gui.drawRelRect(0, height - this.fontRendererObj.FONT_HEIGHT - 4, this.fontRendererObj.getStringWidth("© Mojang AB") + 4, this.fontRendererObj.FONT_HEIGHT + 4, Palette.fromRGBA(0, 0, 0, 0.3f));
        this.drawString(this.fontRendererObj, "© Mojang AB", 2, this.height - 10, -1);
        Gui.drawRelRect(width - this.fontRendererObj.getStringWidth("v1.20.12 (modified)") - 4, height - this.fontRendererObj.FONT_HEIGHT - 4, this.fontRendererObj.getStringWidth("v1.20.12 (modified)") + 4, this.fontRendererObj.FONT_HEIGHT + 4, Palette.fromRGBA(0, 0, 0, 0.3f));
        this.drawString(this.fontRendererObj, "v1.20.12 (modified)", this.width - this.fontRendererObj.getStringWidth("v1.20.12 (modified)") - 2, this.height - 10, -1);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        loadingPopup.draw(mouseX, mouseY);
        LoadingPopup.active = loadingPopup.visible;
    }
}
