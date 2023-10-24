package day.trippin;

import WizClient.Palette;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;

public class TripGuiInput extends TripGuiContainer {
	public String contents = "";
	public final String placeholder;
	protected boolean focused = false;
	protected int cursorPos = 0;
	protected int selectionPos = -1;
	protected int ticks = 0;
	
	public TripGuiInput(int x, int y, int w, int h, String def, String placeholder) {
		super(TripGuiLayoutType.STATIC, x, y, w, h);
		this.placeholder = placeholder;
		if (def != null && def.length() > 0) {
			contents = def;
			cursorPos = def.length();
		}
	}

	@Override
	protected void render(int mx, int my) {
		ticks = (ticks + 1) % 20;
		
		boolean hovered = this.hovered(mx, my);
		Gui.drawRelRect(x, y, width, height, hovered || focused ? Palette.WHITE : Palette.BLACK);
		Gui.drawRelRect(x + 1, y + 1, width - 2, height - 2, Palette.GRAY_DARK);
		int x = this.x + 1;
		int y = this.y + 1;
		int w = width - 2;
		int h = height - 2;
		Gui.drawRect(x, y, x + w, y + 1, Palette.ICON_GRAY);
		Gui.drawRect(x, y + h, x + w, y + h - 1, Palette.ICON_GRAY_LIGHT);
		
		if (ticks >= 10 && this.focused) {
			String c = contents.substring(0, cursorPos);
			Gui.drawRelRect(this.x + mc.fontRendererObj.getStringWidth(c) + 4, this.y + 3, 1, this.height - 7, Palette.GREEN_LIGHT);
		}
		if (this.contents.length() > 0 || this.focused) {
			mc.fontRendererObj.drawString(this.contents, this.x + 4, this.y + this.height / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, Palette.WHITE);
		} else {
			mc.fontRendererObj.drawString(this.placeholder, this.x + 4, this.y + this.height / 2 - mc.fontRendererObj.FONT_HEIGHT / 2, Palette.GRAY_LIGHT);
		}
	}

	@Override
	public void onClick() throws Exception {
		System.out.println("JAI DIE");
		this.focused = true;
	}
	
	@Override
	protected void onClickOff() throws Exception {
		System.out.println("JAI DIE2");
		this.focused = false;
	}

	@Override
	protected boolean onKey(char character, int keycode) throws Exception {
		if (this.focused) {
			if (GuiScreen.isAltKeyDown()) {
				return super.onKey(character, keycode);
			}
			if (keycode == 203) {
				if (GuiScreen.isCtrlKeyDown()) {
					cursorPos = 0;
				} else if (cursorPos > 0) {
					cursorPos--;
				}
				return true;
			}
			if (keycode == 205) {
				if (GuiScreen.isCtrlKeyDown()) {
					cursorPos = contents.length();
				} else if (cursorPos < contents.length()) {
					cursorPos++;
				}
				return true;
			}
			if (keycode == 14) {
				if (GuiScreen.isCtrlKeyDown()) {
					contents = "";
					cursorPos = 0;
				} else {
					if (this.contents.length() > 0) {
						contents = contents.substring(0, cursorPos - 1) + contents.substring(cursorPos);
						cursorPos--;
					}
				}
				return true;
			}
			if (GuiScreen.isCtrlKeyDown()) {
				return true;
			}
			
			if (!ChatAllowedCharacters.isAllowedCharacter(character)) {
				return true;
			}
			contents = contents.substring(0, cursorPos) + character + contents.substring(cursorPos, contents.length());
			this.cursorPos++;
			return true;
		}
		
		return super.onKey(character, keycode);
	}
}
