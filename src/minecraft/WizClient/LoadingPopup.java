package WizClient;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class LoadingPopup {
	private static RenderPopup popup = null;
	public static boolean active;
	
	public static final RenderPopup LOADING = new RenderPopup() {
		@Override
		public void render(int mx, int my, int w, int h) {
			Gui.drawRelRectCentered(MathHelper.floor_double(w / 2d), MathHelper.floor_double(h / 2), 150, 100, Palette.GRAY);
		}};
	
	public static boolean active() {
		// return popup != null;
		return active;
	}
	
	public static void clear() {
		popup = null;
	}
	
	public static void set(RenderPopup a) {
		popup = a;
	}
	
	public static interface RenderPopup {
		void render(int mx, int my, int width, int height);
	}
}
