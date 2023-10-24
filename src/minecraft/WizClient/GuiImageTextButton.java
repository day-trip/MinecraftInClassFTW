package WizClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiImageTextButton extends GuiButton {
	private final ResourceLocation icon;
	private final ResourceLocation image;

	public GuiImageTextButton(int buttonId, int x, int y, String buttonText, ResourceLocation icon, ResourceLocation image) {
		super(buttonId, x, y, buttonText);
		this.icon = icon;
		this.image = image;
	}

	public GuiImageTextButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, ResourceLocation icon, ResourceLocation image) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.icon = icon;
		this.image = image;
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    	if (!this.visible) {
    		return;
    	}
    	
        this.hovered = !LoadingPopup.active() && mouseX >= this.x && mouseY >= this.yPosition && mouseX < this.x + this.width && mouseY < this.yPosition + this.height;
        
        int rbh = mc.fontRendererObj.FONT_HEIGHT + 14;
        int rth = this.height - rbh;
        Gui.drawRelRect(this.x, this.yPosition, this.width, rbh + rth, this.hovered ? Palette.WHITE : Palette.BLACK);
        Gui.drawRelRect(this.x + 1, this.yPosition + 1, this.width - 2, rth, this.hovered ? Palette.ICON_GREEN : Palette.ICON_GRAY);
        Gui.drawRelRect(this.x + 1, this.yPosition + rth, this.width - 2, rbh - 1, this.hovered ? Palette.ICON_GREEN_DARK : Palette.IMAGE_GREEN);
        Palette.drawGlint(this.x + 1, this.yPosition + 1, this.width - 2, this.height - 2, this.hovered ? Palette.ICON_GREEN_LIGHT : Palette.ICON_GRAY_LIGHT, this.hovered ? Palette.ICON_GREEN_XDARK : Palette.ICON_GRAY_XDARK);        
        
        this.mouseDragged(mc, mouseX, mouseY);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.icon);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(this.x + 4, this.yPosition + rth + (rbh / 2) - 8, 0, 0, 16, 17, 16, 17);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.image);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(this.x + 2, this.yPosition + 2, 0, 0, this.width - 4, rth - 2, 260, 150);
        
        mc.fontRendererObj.drawString(this.displayString, this.x + 24, this.yPosition + rth + 7, this.hovered ? Palette.WHITE : Palette.WHITE);
    }
}
