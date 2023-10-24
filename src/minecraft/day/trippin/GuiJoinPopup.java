package day.trippin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerAddress;

import java.util.concurrent.Callable;

import WizClient.LoadingPopup;
import WizClient.Palette;

public class GuiJoinPopup extends TripGuiContainer {	
	private final TripGuiInput input;
	
	public GuiJoinPopup(int x, int y, GuiScreen screen) {
		super(TripGuiLayoutType.STATIC, x, y, 245, 135);
		this.anchor = TripGuiContainer.TripGuiAnchor.CENTER;
		
		this.addChild(new TripGuiButton(width / 2 - 16, -(height / 2) + 4, 12, 12, "x", new Callable() {
			@Override
			public Object call() throws Exception {
				GuiJoinPopup.this.visible = false;
				return null;
			}
		}));
		
		this.input = new TripGuiInput(-(width / 2) + 13, -(height / 2) + 75, width - 27, 20, mc.gameSettings.smoothCamera, "Connection ID");
		input.focused = true;
		this.addChild(input);
		
		this.addChild(new TripGuiButton(-(width / 2) + 13, -(height / 2) + 100, width - 27, 20, "Join", new Callable() {
			@Override
			public Object call() throws Exception {
				if (input.contents.length() > 2 && input.contents.contains(".")) {
					GuiJoinPopup.this.visible = false;
					LoadingPopup.active = false;
					ServerAddress address = ServerAddress.fromString(input.contents);
					mc.gameSettings.smoothCamera = input.contents;
					mc.displayGuiScreen(new GuiConnecting(screen, mc, address.getIP(), address.getPort()));
				}
				return null;
			}
		}));
	}
	
	protected void render(int mx, int my) {
		Gui.drawRect(0, 0, GuiScreen.width, GuiScreen.height, Palette.fromRGBA(0, 0, 0, 0.75f));
		this.x = GuiScreen.width / 2;
		this.y = GuiScreen.height / 2;
		Gui.drawRelRectCentered(x, y, width, height, Palette.BLACK);
		Gui.drawRelRectCentered(x, y, width - 2, height - 2, Palette.GRAY);
		Palette.drawGlintCentered(x, y, width - 2, height - 2, Palette.GRAY_LIGHT, Palette.GRAY_DARK);
		Gui.drawCenteredString(mc.fontRendererObj, "Join World", x, y - (height / 2) + 8, Palette.TEXT_DARK);
		
		Gui.drawRelRectCentered(x, y + 6, width - 15, height - 30, Palette.BLACK);
		Palette.drawGlintCentered(x, y + 6, width - 15, height - 30, Palette.GRAY_DARK, Palette.GRAY_LIGHT);
		mc.fontRendererObj.drawSplitString("Join codes are not avaliable right now. Enter the connection ID of the world you would like to join.", x - (width / 2) + 12, y - (height / 2) + 25, this.width - 20, Palette.WHITE);
	}

	@Override
	protected boolean onKey(char character, int keycode) throws Exception {
		if ((keycode == 28 || keycode == 156) && input.focused) {
			((TripGuiButton) this.children.get(2)).onClick();
			return true;
		}
		return super.onKey(character, keycode);
	}
}
 