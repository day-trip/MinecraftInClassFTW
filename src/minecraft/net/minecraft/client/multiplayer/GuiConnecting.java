package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import WizClient.Palette;

public class GuiConnecting extends GuiScreen
{
    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    
    private NetworkManager networkManager;
    private boolean cancel;
    private final GuiScreen previousGuiScreen;
    private NetHandlerPlayClient handler;
    private int progress;
    
    private final boolean single;

    public GuiConnecting(GuiScreen p_i1182_1_, Minecraft mcIn, String hostName, int port)
    {
        this(p_i1182_1_, mcIn, false);
        mcIn.loadWorld((WorldClient)null);
        this.connect(hostName, port);
    }
    
    public GuiConnecting(Minecraft mcIn, NetHandlerPlayClient handler)
    {
        this(null, mcIn, false);
        this.handler = handler;
    }
    
    public GuiConnecting(Minecraft mcIn)
    {
        this(null, mcIn, true);
    }
    
    public GuiConnecting(GuiScreen previous, Minecraft mc, boolean single) {
    	this.previousGuiScreen = previous;
    	if (previous != null) {
    		this.panoramaTimer = previous.panoramaTimer;
    	}
    	this.mc = mc;
    	this.single = single;
    }

    private void connect(final String ip, final int port)
    {
        logger.info("Connecting to " + ip + ", " + port);
        (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
        {
            public void run()
            {
                InetAddress inetaddress = null;

                try
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }

                    inetaddress = InetAddress.getByName(ip);
                    GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiConnecting.this.mc.gameSettings.isUsingNativeTransport());
                    GuiConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
                    GuiConnecting.this.networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
                    GuiConnecting.this.networkManager.sendPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
                }
                catch (UnknownHostException unknownhostexception)
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiConnecting.logger.error((String)"Couldn\'t connect to server", (Throwable)unknownhostexception);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {"Unknown host"})));
                }
                catch (NullPointerException exception) {
                	String s = "You need to sign in dummy! (Click Manage Account)";
                	GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {s})));
                }
                catch (Exception exception)
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiConnecting.logger.error((String)"Couldn\'t connect to server", (Throwable)exception);
                    String s = exception.toString();

                    if (inetaddress != null)
                    {
                        String s1 = inetaddress.toString() + ":" + port;
                        s = s.replaceAll(s1, "");
                    }

                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] {s})));
                }
            }
        }).start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    	super.updateScreen();
    	if (this.handler != null) {
    		++this.progress;

            if (this.progress % 20 == 0)
            {
                this.handler.addToSendQueue(new C00PacketKeepAlive());
            }
    	} else if (this.networkManager != null)
        {
            if (this.networkManager.isChannelOpen())
            {
                this.networkManager.processReceivedPackets();
            }
            else
            {
                this.networkManager.checkDisconnected();
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	this.drawPanoramaBackground(partialTicks);
    	
    	this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(width / 2 - 96, height / 30, 0, 0, 1024, 372, 192, 70, 1024f, 372f);
        
        int x = GuiScreen.width / 2;
		int y = (int) (GuiScreen.height / 1.8f);
		int w = 275;
		int h = 100;
		Gui.drawRelRectCentered(x, y, w, h, Palette.BLACK);
		Gui.drawRelRectCentered(x, y, w - 2, h - 2, Palette.GRAY);
		Palette.drawGlintCentered(x, y, w - 2, h - 2, Palette.GRAY_LIGHT, Palette.GRAY_DARK);
		Gui.drawCenteredString(mc.fontRendererObj, "Joining", x, y - (h / 2) + 9, Palette.TEXT_DARK);
		
		Gui.drawRelRectCentered(x, y + 6, w - 15, h - 30, Palette.BLACK);
		Palette.drawGlintCentered(x, y + 6, w - 15, h - 30, Palette.GRAY_DARK, Palette.GRAY_LIGHT);
		String s = "Connecting...";
		if (handler != null) {
			s = "Generating world";
		} else if (this.single) {
			s = "Loading resources";
		} else if (networkManager != null) {
			s = "Logging in";
		}
		Gui.drawCenteredString(mc.fontRendererObj, s, x, y - (h / 2) + 30, Palette.WHITE);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
