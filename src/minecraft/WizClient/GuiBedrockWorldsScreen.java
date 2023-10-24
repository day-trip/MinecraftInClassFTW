package WizClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import day.trippin.TripGuiInput;
import day.trippin.TripGuiContainer;
import day.trippin.TripGuiContainer.TripGuiLayoutType;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.SaveFormatComparator;

public class GuiBedrockWorldsScreen extends GuiScreen {
	protected final GuiScreen previous;
	protected final TripGuiInput search;
	protected final TripGuiContainer grid;
	protected List<SaveFormatComparator> levels;
	protected String lastSearch;
		
	public GuiBedrockWorldsScreen(GuiScreen previous) {
		this.previous = previous;
		this.panoramaTimer = previous.panoramaTimer;
		this.search = new TripGuiInput(12, 30, width - 24, 25, "", "Search");
		this.grid = new TripGuiContainer(TripGuiLayoutType.GRID, 0, 65, width, height - 50).setMargainX(12).setSpacing(5).setRows(3);
	}
	
	protected void setupChildren() {
		this.grid.clearChildren();
		List<SaveFormatComparator> list = this.levels.stream().filter(x -> x.getDisplayName().contains(this.search.contents)).collect(Collectors.toList());
		for (SaveFormatComparator sfc : list) {
			this.grid.addChild(new TripWorldInstance(110, sfc));
		}
		this.grid.build();
	}
	
	private void loadLevelList()
    {
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        try {
			this.levels = isaveformat.getSaveList();
		} catch (AnvilConverterException e) {
			System.err.println("Could not load levels!");
			e.printStackTrace();
		}
        Collections.sort(this.levels);
    }

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawPanoramaBackground(partialTicks);
		// Navbar
		Gui.drawRelRect(0, 0, this.width, 20, Palette.GRAY);
		Gui.drawRelRect(0, 20, this.width, 2, Palette.GRAY_DARK);
		Gui.drawRelRect(0, 22, this.width, 1, Palette.BLACK);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(IconAsset.GLOBE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(22, 2, 0, 0, 16, 17, 16, 17);
        
		this.mc.fontRendererObj.drawString("My Worlds", 44, 10 - (mc.fontRendererObj.FONT_HEIGHT / 2), Palette.TEXT_DARK);
		
		search.draw(mouseX, mouseY);
		grid.draw(mouseX, mouseY);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		if (this.lastSearch != search.contents) {
			this.setupChildren();
		}
		this.lastSearch = search.contents;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 2) {
			previous.panoramaTimer = this.panoramaTimer;
			mc.displayGuiScreen(previous);
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButton(2, 4, 4, 12, 12, "<"));
		this.loadLevelList();
		this.setupChildren();
	}
	
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (search.acceptKey(typedChar, keyCode)) {
			return;
		}
		super.keyTyped(typedChar, keyCode);
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseButton == 0) {
			this.search.acceptClick(mouseX, mouseY);
		}
		
		this.grid.acceptClick(mouseX, mouseY);
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	public static class TripWorldInstance extends TripGuiContainer {
		protected final SaveFormatComparator sfc;
		
		public TripWorldInstance(int h, SaveFormatComparator sfc) {
			super(TripGuiLayoutType.STATIC, 0, 0, 0, h);
			this.sfc = sfc;
		}

		@Override
		protected void render(int mx, int my) {
			boolean hovered = this.hovered(mx, my);
			
			int rbh = this.height / 3;
	        int rth = this.height - rbh;
	        Gui.drawRelRect(this.x, this.y, this.width, rbh + rth, hovered ? Palette.WHITE : Palette.BLACK);
	        Gui.drawRelRect(this.x + 1, this.y + 1, this.width - 2, rth, hovered ? Palette.ICON_GREEN : Palette.ICON_GRAY);
	        Gui.drawRelRect(this.x + 1, this.y + rth, this.width - 2, rbh - 1, hovered ? Palette.GREEN : Palette.ICON_GRAY_DARK);
	        Palette.drawGlint(this.x + 1, this.y + 1, this.width - 2, this.height - 2, hovered ? Palette.ICON_GREEN_LIGHT : Palette.ICON_GRAY_LIGHT, hovered ? Palette.ICON_GREEN_XDARK : Palette.ICON_GRAY_XDARK); 
	        
			Minecraft.getMinecraft().getTextureManager().bindTexture(IconAsset.VIEW_WORLDS);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        Gui.drawModalRectWithCustomSizedTexture(this.x + 2, this.y + 2, 0, 0, this.width - 4, rth - 2, 260, 150);
	        
	        mc.fontRendererObj.drawString(this.sfc.getDisplayName(), this.x + 5, this.y + rth + 3, Palette.WHITE);
	        mc.fontRendererObj.drawString(this.sfc.getEnumGameType().toString(), this.x + 5, this.y + rth + 5 + mc.fontRendererObj.FONT_HEIGHT, Palette.GRAY);
		}

		@Override
		public void onClick() throws Exception {
			mc.displayGuiScreen(new GuiConnecting(mc));
			mc.updateDisplay();
			this.mc.launchIntegratedServer(this.sfc.getFileName(), this.sfc.getDisplayName(), (WorldSettings)null);
		}
	}
}
