package WizClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiIconTextButton extends GuiButton {
	private final ResourceLocation icon;
	
	public GuiIconTextButton(int buttonId, int x, int y, String buttonText, ResourceLocation icon) {
		super(buttonId, x, y, buttonText);
		this.icon = icon;
	}

	public GuiIconTextButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, ResourceLocation icon) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.icon = icon;
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    	if (!this.visible) {
    		return;
    	}
    	
        this.hovered = !LoadingPopup.active() && mouseX >= this.x && mouseY >= this.yPosition && mouseX < this.x + this.width && mouseY < this.yPosition + this.height;
        
        int rbh = mc.fontRendererObj.FONT_HEIGHT + 10;
        int rth = this.height - rbh;
        Gui.drawRelRect(this.x, this.yPosition, this.width, rbh + rth, this.hovered ? Palette.WHITE : Palette.BLACK);
        Gui.drawRelRect(this.x + 1, this.yPosition + 1, this.width - 2, rth, this.hovered ? Palette.ICON_GREEN : Palette.ICON_GRAY);
        Gui.drawRelRect(this.x + 1, this.yPosition + rth, this.width - 2, rbh - 1, this.hovered ? Palette.ICON_GREEN_DARK : Palette.ICON_GRAY_DARK);
        Palette.drawGlint(this.x + 1, this.yPosition + 1, this.width - 2, this.height - 2, this.hovered ? Palette.ICON_GREEN_LIGHT : Palette.ICON_GRAY_LIGHT, this.hovered ? Palette.ICON_GREEN_XDARK : Palette.ICON_GRAY_XDARK);        
        
        this.mouseDragged(mc, mouseX, mouseY);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.icon);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(this.x + this.width / 2 - 8, this.yPosition + (rth / 2) - 8, 0, 0, 16, 17, 16, 17);
        
        Gui.drawCenteredString(mc.fontRendererObj, this.displayString, this.x + this.width / 2, this.yPosition + rth + 5, this.hovered ? Palette.WHITE : Palette.WHITE);
    }

}
