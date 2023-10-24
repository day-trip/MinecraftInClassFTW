package net.minecraft.client.gui;

import WizClient.LoadingPopup;
import WizClient.Palette;
import WizClient.util.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui
{
	protected static ResourceLocation buttonTextures = new ResourceLocation("WizClient/gui/Button.png");
	
    protected int width;

    protected int height;

    public int x;

    public int yPosition;

    public String displayString;
    public int id;

    public boolean enabled;

    public boolean visible;
    protected boolean hovered;

    public GuiButton(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.x = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver)
    {
        int i = 1;

        if (!this.enabled)
        {
            i = 0;
        }
        else if (mouseOver)
        {
            i = 2;
        }

        return i;
    }
    
    public void drawButton(Minecraft mc, int mx, int my) {
    	this.drawButton(mc, mx, my, false);
    }
    
    public void drawButton(Minecraft mc, int mouseX, int mouseY, boolean popup) {
    	if (!this.visible) {
    		return;
    	}
    	
        this.hovered = mouseX >= this.x && mouseY >= this.yPosition && mouseX < this.x + this.width && mouseY < this.yPosition + this.height;
        if (!popup && LoadingPopup.active()) {
        	this.hovered = false;
        }
        Gui.drawRect(this.x, this.yPosition, this.x + this.width, this.yPosition + this.height, this.hovered ? Palette.WHITE : Palette.BLACK);
        Gui.drawRect(this.x + 1, this.yPosition + 1, this.x + this.width - 1, this.yPosition + this.height - 1, this.hovered ? Palette.GREEN : Palette.GRAY);
        
        Palette.drawGlint(this.x + 1, this.yPosition + 1, this.width - 2, this.height - 2, this.hovered ? Palette.GREEN_LIGHT : Palette.GRAY_LIGHT, this.hovered ? Palette.GREEN_DARK : Palette.GRAY_DARK);
        
        this.mouseDragged(mc, mouseX, mouseY);
        
        Gui.drawCenteredString(mc.fontRendererObj, this.displayString, this.x + this.width / 2, this.yPosition + (this.height - 8) / 2, this.hovered ? Palette.WHITE : Palette.TEXT_DARK);
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.x && mouseY >= this.yPosition && mouseX < this.x + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver()
    {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
    }

    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
}
