package WizClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiClearIconButton extends GuiButton {
	protected final ResourceLocation image;
	
	public GuiClearIconButton(int buttonId, int x, int y, ResourceLocation image) {
		super(buttonId, x, y, "");
		this.image = image;
	}

	public GuiClearIconButton(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation image) {
		super(buttonId, x, y, widthIn, heightIn, "");
		this.image = image;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		mc.getTextureManager().bindTexture(this.image);
		Gui.drawScaledCustomSizeModalRect(x, yPosition, 0, 0, 16, 16, this.width, this.height, 16, 16);
	}
}
