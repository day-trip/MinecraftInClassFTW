package net.minecraft.client.gui;

import java.io.IOException;

import WizClient.Palette;
import WizClient.gui.ModListGui;
import WizClient.gui.modoptions.GuiModToggle;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MathHelper;

public class GuiIngameMenu extends GuiScreen
{
	protected final int LEFT_START = 36;
	protected final int WIDTH;
	
	public GuiIngameMenu() {
		super();
		WIDTH = width > 1000 ? 175 : 150;
	}
	
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        
        this.buttonList.add(new GuiButton(0, LEFT_START + 6 + 4, 36, WIDTH - 12 - 8, 20, "OPTIONS"));
        
        this.buttonList.add(new GuiButton(6, LEFT_START + 6 + 4, 36 + 20 + 5, WIDTH - 12 - 8, 20, "STATISTICS"));
        
        this.buttonList.add(new GuiButton(8, LEFT_START + 6 + 4, 36 + 40 + 10, WIDTH - 12 - 8, 20, "MODS"));
        
        this.buttonList.add(new GuiButton(1, LEFT_START + 6 + 4, 36 + 60 + 15, WIDTH - 12 - 8, 20, "SAVE & EXIT"));   
               
        this.buttonList.add(new GuiButton(4, 4, 4, 12, 12, "<"));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;

            case 1:
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);

                this.mc.displayGuiScreen(new GuiMainMenu());

            case 2:
            case 3:
            default:
                break;

            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;

            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;

            case 7:
                this.mc.displayGuiScreen(new GuiModToggle());
                
            case 8:
            	// this.mc.displayGuiScreen(new ModListGui());
            	this.mc.displayGuiScreen(new GuiModToggle());
            	break;
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        // this.drawDefaultBackground();
        
        // Paper doll
        GuiInventory.drawEntityOnScreen(width - 75, MathHelper.floor_float(height / 1.1f), MathHelper.floor_float(height / 2.5f), mouseX, mouseY, mc.thePlayer);
        
        // Navbar
     	Gui.drawRelRect(0, 0, GuiScreen.width, 20, Palette.GRAY);
     	Gui.drawRelRect(0, 20, GuiScreen.width, 2, Palette.GRAY_DARK);
     	Gui.drawRelRect(0, 22, GuiScreen.width, 1, Palette.BLACK);
     	this.mc.fontRendererObj.drawString("Resume Game", 19, 10 - (mc.fontRendererObj.FONT_HEIGHT / 2), Palette.TEXT_DARK);
        
     	int my = 6;
     	int imx = 6;
        Gui.drawRelRect(LEFT_START, 30, WIDTH, height - 31 - my, Palette.BLACK);
        Gui.drawRelRect(LEFT_START + 1, 31, WIDTH - 2, height - 31 - my, Palette.GRAY);
        Palette.drawGlint(LEFT_START + 1, 31, WIDTH - 2, height - 31 - my, Palette.GRAY_LIGHT, Palette.GRAY_DARK);
        Gui.drawRelRect(LEFT_START + imx, 34, WIDTH - (imx * 2), height - 40 - my, Palette.BLACK);
        Palette.drawGlint(LEFT_START + imx, 34, WIDTH - (imx * 2), height - 40 - my, Palette.GRAY_DARK, Palette.GRAY_LIGHT);
        
        // TODO: dont render hotbar.
     	
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
