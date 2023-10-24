package day.trippin;

import WizClient.Palette;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class GuiLoadingPopup extends TripGuiContainer {
	public GuiLoadingPopup(int x, int y) {
		super(TripGuiLayoutType.STATIC, x, y, 275, 100);
	}
	
	protected void render(int mx, int my) {
		Gui.drawRect(0, 0, GuiScreen.width, GuiScreen.height, Palette.fromRGBA(0, 0, 0, 0.75f));
		this.x = GuiScreen.width / 2;
		this.y = GuiScreen.height / 2;
		Gui.drawRelRectCentered(x, y, width, height, Palette.BLACK);
		Gui.drawRelRectCentered(x, y, width - 2, height - 2, Palette.GRAY);
		Palette.drawGlintCentered(x, y, width - 2, height - 2, Palette.GRAY_LIGHT, Palette.GRAY_DARK);
		Gui.drawCenteredString(mc.fontRendererObj, "Logging in", x, y - (height / 2) + 8, Palette.TEXT_DARK);
		
		Gui.drawRelRectCentered(x, y + 6, width - 15, height - 30, Palette.BLACK);
		Palette.drawGlintCentered(x, y + 6, width - 15, height - 30, Palette.GRAY_DARK, Palette.GRAY_LIGHT);
		Gui.drawCenteredString(mc.fontRendererObj, "Stop ruining your eyes bruh.", x, y - (height / 2) + 30, Palette.WHITE);
		// mc.fontRendererObj.drawSplitString("Join codes are not avaliable right now. Enter the connection ID of the world you would like to join.", x - (width / 2) + 12, y - (height / 2) + 25, this.width - 20, Palette.WHITE);
	}
}
