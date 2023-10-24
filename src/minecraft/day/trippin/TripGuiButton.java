package day.trippin;

import java.util.concurrent.Callable;

import WizClient.Palette;
import net.minecraft.client.gui.Gui;

public class TripGuiButton extends TripGuiContainer {
	protected final String text;
	protected final Callable handler;
	
	public boolean enabled = true;
	
	public TripGuiButton(int x, int y, int w, int h, String text, Callable handler) {
		super(TripGuiLayoutType.STATIC, x, y, w, h);
		this.text = text;
		this.handler = handler;
	}

	public void render(int mx, int my) {
		boolean hovered = this.hovered(mx, my);
		// System.out.println(mx + " & " + my);
		// System.out.println(hovered);
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, hovered ? Palette.WHITE : Palette.BLACK);
        Gui.drawRect(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, hovered ? Palette.GREEN : Palette.GRAY);
        Palette.drawGlint(this.x + 1, this.y + 1, this.width - 2, this.height - 2, hovered ? Palette.GREEN_LIGHT : Palette.GRAY_LIGHT, hovered ? Palette.GREEN_DARK : Palette.GRAY_DARK);
        
        
        Gui.drawCenteredString(mc.fontRendererObj, this.text, this.x + this.width / 2, this.y + (this.height - 8) / 2, hovered ? Palette.WHITE : Palette.TEXT_DARK);
	}
	
	public void onClick() throws Exception {
		this.handler.call();
	}
}
