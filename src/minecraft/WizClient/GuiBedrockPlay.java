package WizClient;

import java.io.IOException;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import day.trippin.GuiJoinPopup;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;

public class GuiBedrockPlay extends GuiScreen {
	private static final Logger logger = LogManager.getLogger();
	
	private final GuiScreen previous;	
	private final GuiJoinPopup joinPopup;
	
	public GuiBedrockPlay(GuiScreen previous) {
		this.previous = previous;
		this.panoramaTimer = previous.panoramaTimer;
		this.joinPopup = new GuiJoinPopup(width / 2, height / 2, this);
		joinPopup.visible = false;
		joinPopup.build();
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawPanoramaBackground(partialTicks);
		// Navbar
		Gui.drawRelRect(0, 0, this.width, 20, Palette.GRAY);
		Gui.drawRelRect(0, 20, this.width, 2, Palette.GRAY_DARK);
		Gui.drawRelRect(0, 22, this.width, 1, Palette.BLACK);
		this.mc.fontRendererObj.drawString("Play", 19, 10 - (mc.fontRendererObj.FONT_HEIGHT / 2), Palette.TEXT_DARK);
		
		// Divider line
		int w = width;
		int h = height;
		int mx = 10;
		int my = 12;
		int by = h - 50 - my;
		int sy = 10;
		Gui.drawRect(mx, by - (sy / 2), w - mx, by - (sy / 2) + 2, Palette.fromRGBA(255, 255, 255, 0.15f));
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		this.joinPopup.update(width / 2, height / 2);
		// aSystem.out.println(mx + " - " + my);
		this.joinPopup.draw(mouseX, mouseY);
		LoadingPopup.active = joinPopup.visible;
	}
	
	public void initGui() {
		super.initGui();
		int w = width;
		int h = height;
		int mx = 10;
		int my = 12;
		int bw = ((w - mx) / 2) - 4;
		int by = h - 50 - my;
		
		this.buttonList.add(new GuiIconTextButton(0, mx, by, bw, 50, "CREATE NEW", IconAsset.PLUS));
		this.buttonList.add(new GuiIconTextButton(1, w - mx - bw + 2, by, bw, 50, "JOIN WORLD", IconAsset.MULTIPLAYER));
		
		int blah = (int) (h / 1.75);
		int ih = by - blah - 10;
		this.buttonList.add(new GuiImageTextButton(3, mx, ih, bw, blah, "VIEW MY WORLDS", IconAsset.GLOBE, IconAsset.VIEW_WORLDS));
		this.buttonList.add(new GuiImageTextButton(4, w - mx - bw + 2, ih, bw, blah, "VIEW LIBRARY", IconAsset.BOOKSHELF, IconAsset.VIEW_WORLDS));
		
		this.buttonList.add(new GuiButton(2, 4, 4, 12, 12, "<"));
	}
	
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			mc.displayGuiScreen(new GuiCreateWorld(this));
		}
		if (button.id == 1) {
			joinPopup.visible = true;
		}
		if (button.id == 2) {
			previous.panoramaTimer = this.panoramaTimer;
			mc.displayGuiScreen(previous);
		}
		if (button.id == 3) {
			mc.displayGuiScreen(new GuiBedrockWorldsScreen(this));
		}
		
		if (button.id == 4) {
			try
            {
                Class<?> oclass = Class.forName("java.awt.Desktop");
                Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI("https://kcls.org")});
            }
            catch (Throwable throwable)
            {
                logger.error("Couldn\'t open link", throwable);
            }
		}
	}
	
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1 && joinPopup.visible) {
			joinPopup.visible = false;
			return;
		}
		if (joinPopup.acceptKey(typedChar, keyCode)) {
			return;
		}
		super.keyTyped(typedChar, keyCode);
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 0 && joinPopup.visible) {
			this.joinPopup.acceptClick(mouseX, mouseY);
			return;
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
