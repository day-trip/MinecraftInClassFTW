package WizClient;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Palette {
	public static final int BLACK = Palette.fromRGBA(0, 0, 0, 1);
	public static final int WHITE = Palette.fromRGBA(255, 255, 255, 1);
	
	public static final int GRAY = Palette.fromRGBA(198, 198, 198, 1);
	public static final int GRAY_LIGHT = Palette.fromRGBA(247, 247, 247, 1);
	public static final int GRAY_DARK = Palette.fromRGBA(110, 110, 110, 1);
	
	public static final int GREEN = Palette.fromRGBA(33, 131, 6, 1);
	public static final int GREEN_LIGHT = Palette.fromRGBA(23, 205, 7, 1);
	public static final int GREEN_DARK = Palette.fromRGBA(0, 78, 0, 1);
	
	public static final int TEXT_DARK = Palette.fromRGBA(77, 77, 77, 1);
	
	public static final int SPLASH = Palette.fromRGBA(30, 30, 30, 1);
	
	public static final int ICON_GRAY = Palette.fromRGBA(58, 58, 58, 1);
	public static final int ICON_GRAY_DARK = Palette.fromRGBA(29, 29, 29, 1);
	public static final int ICON_GRAY_XDARK = Palette.fromRGBA(35, 35, 35, 1);
	public static final int ICON_GRAY_LIGHT = Palette.fromRGBA(137, 137, 137, 1);
	
	public static final int ICON_GREEN = Palette.fromRGBA(74, 206, 74, 1);
	public static final int ICON_GREEN_DARK = Palette.fromRGBA(0, 58, 3, 1);
	public static final int ICON_GREEN_LIGHT = Palette.fromRGBA(146, 226, 146, 1);
	public static final int ICON_GREEN_XDARK = Palette.fromRGBA(44, 124, 44, 1);
	
	public static final int IMAGE_GREEN = Palette.fromRGBA(5, 99, 5, 1);
	
	public static int fromRGBA(int red, int green, int blue, float alpha) {
        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));
        alpha = Math.min(1.0F, Math.max(0.0F, alpha));
        int a = Math.round(alpha * 255.0F);
        int packedColor = (a << 24) | (red << 16) | (green << 8) | blue;
        return packedColor;
    }
	
	public static void drawGlintCentered(int x, int y, int w, int h, int c1, int c2) {
		Palette.drawGlint(x - (w / 2), y - (h / 2), w, h, c1, c2);
	}
	
	public static void drawGlint(int x, int y, int w, int h, int c1, int c2) {
		Gui.drawRect(x, y, x + 1, y + h, c1);
        Gui.drawRect(x, y, x + w, y + 1, c1);
        Gui.drawRect(x, y + h, x + w, y + h - 1, c2);
        Gui.drawRect(x + w, y, x + w - 1, y + h, c2);
	}
	
	public static void drawBorderAndGlint(int x, int y, int w, int h, int c1, int c2, int b1) {
		Gui.drawRect(x, y, x + w, y + h, b1);
		Palette.drawGlint(x + 1, y + 1, w - 2, h - 2, c1, c2);
	}
}
