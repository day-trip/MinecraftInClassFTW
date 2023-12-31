package WizClient.ui.auth;

import java.util.UUID;
import java.util.concurrent.Callable;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;

import WizClient.LoadingPopup;
import WizClient.util.openauth.microsoft.MicrosoftAuthResult;
import WizClient.util.openauth.microsoft.MicrosoftAuthenticationException;
import WizClient.util.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class SessionChanger {

	private static SessionChanger instance;
	private final UserAuthentication auth;

	public static SessionChanger getInstance() {
		if (instance == null) {
			instance = new SessionChanger();
		}

		return instance;
	}
	
	//Creates a new Authentication Service. 
	private SessionChanger() {
		UUID notSureWhyINeedThis = UUID.randomUUID(); //Idk, needs a UUID. Seems to be fine making it random
		AuthenticationService authService = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), notSureWhyINeedThis.toString());
		auth = authService.createUserAuthentication(Agent.MINECRAFT);
		authService.createMinecraftSessionService();
	}

	
	//Online mode
	//Checks if your already loggin in to the account.
	public void setUser(String email, String password) {
		if(!Minecraft.getMinecraft().getSession().getUsername().equals(email) || Minecraft.getMinecraft().getSession().getToken().equals("0")){

			this.auth.logOut();
			this.auth.setUsername(email);
			this.auth.setPassword(password);
			try {
				this.auth.logIn();
				Session session = new Session(this.auth.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(auth.getSelectedProfile().getId()), this.auth.getAuthenticatedToken(), this.auth.getUserType().getName());
				setSession(session);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public void setUserMicrosoft(Callable<Object> cb) {
		System.out.println("We got here #1");
		MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
		authenticator.loginWithAsyncWebview().thenAcceptAsync(acc -> {
	    	Minecraft.getMinecraft().gameSettings.refreshToken = acc.getRefreshToken();
			Minecraft.getMinecraft().session = new Session(acc.getProfile().getName(), acc.getProfile().getId(), acc.getAccessToken(), "legacy");
			try {
				cb.call();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}).exceptionally(e -> {
			System.out.println("We got here #10");
			System.out.println(e.toString());
			try {
				cb.call();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		});
	}

	public void setUserMicrosoft(String email, String password) {
		System.out.println("We got here #1");
		MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
		try {
			MicrosoftAuthResult acc = authenticator.loginWithCredentials(email, password);
			Minecraft.getMinecraft().session = new Session(acc.getProfile().getName(), acc.getProfile().getId(), acc.getAccessToken(), "legacy");

		} catch (MicrosoftAuthenticationException e) {
			System.out.println("We got here #10");
			System.out.println(e.toString());
		}
	}

	//Sets the session.
	//You need to make this public, and remove the final modifier on the session Object.
	private void setSession(Session session) {
		Minecraft.getMinecraft().session = session;
	}

	//Login offline mode
	//Just like MCP does
	public void setUserOffline(String username) {
		this.auth.logOut();
		Session session = new Session(username, username, "0", "legacy");
		setSession(session);
	}

}