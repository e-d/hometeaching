package com.edwardstlouis.client.view.impl;

import java.util.Date;

import com.edwardstlouis.client.ClientFactory;
import com.edwardstlouis.client.events.LoginChangeEvent;
import com.edwardstlouis.client.events.LoginChangeEvent.LoginEvent;
import com.edwardstlouis.client.events.LoginChangeEventHandler;
import com.edwardstlouis.client.widgets.LoginWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationShell extends Composite {
	
	private static final long HOUR = 1000 * 60 * 60;
	private static final long YEAR = HOUR * 24 * 365;

	// Cookies are stored by random characters as to not draw attention to would-be hackers.
	private static final String AUTHENTICATED_PASSWORD_KEY = "kISgH8js654SG0gpHiwj";
	private static final String AUTHENTICATED_EMAIL_KEY = "RcrFgNO6549sYGrpUHha";
	private static final String IS_USER_AUTHENTICATED_KEY = "djW3m654WHn3Lgrwsbgh";
	
	interface MyUiBinder extends UiBinder<Widget, ApplicationShell> {}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private ClientFactory clientFactory;
	
	@UiField HTMLPanel page;
	@UiField Anchor visitsLink;
	@UiField Anchor eventsLink;
	@UiField Anchor contactUsLink;
	@UiField HorizontalPanel menuButtonsPanel;
	@UiField FlowPanel localeAndUserInfo;
	@UiField HTMLPanel loginForm;
	@UiField(provided=true) LoginWidget login;
	@UiField HTMLPanel loginInfo;
	@UiField Label userEmail;
	@UiField Label companyName;
	@UiField Anchor logoutLink;
	
	public ApplicationShell (ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.login = new LoginWidget(clientFactory);
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setActiveLink(String link) {
		visitsLink.removeStyleName("active");
		eventsLink.removeStyleName("active");
		contactUsLink.removeStyleName("active");
		
		if ("visits".equals(link)) {
			visitsLink.addStyleName("active");
		} else if ("events".equals(link)) {
			eventsLink.addStyleName("active");
		} else if ("contactUs".equals(link)) {
			contactUsLink.addStyleName("active");
		}
	}
	
	private boolean isInitialized = false;
	
	@Override
	protected void onLoad() {
		if (!isInitialized) {
			
			// Check to see if the user is already logged-in.
			if (ApplicationShell.isUserAuthenticated()) {
				hideLoginForm();
			} else {
				showLoginForm();
			}
			
			// Add event handlers for logging in/out.
			clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {
				@Override
				public void onLoginChange(LoginChangeEvent loginChangeEvent) {
					switch (loginChangeEvent.getLoginEvent()) {
					case LOGIN:
						hideLoginForm();
						break;
					case LOGOUT:
					default:
						showLoginForm();
						break;
					}
				}
			});
			
			// Renew the "session" every time the page changes.
			History.addValueChangeHandler(new ValueChangeHandler<String>() {
				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					checkLoginStatus();
				}
			});
			
			checkLoginStatus();
			isInitialized = true;
		}

		super.onLoad();
	}

	public void checkLoginStatus() {
//		clientFactory.getRequestFactory().userControllerRequest().getNewAuthenticationToken(ApplicationShell.getAuthenticationToken()).fire(new Receiver<String>() {
//			@Override
//			public void onSuccess(String response) {
//				// If it could not renew the token, and the client thinks we're logged in, let's notify the user.
//				if (response.equals("") && ApplicationShell.isUserAuthenticated()) {
//					Window.alert("Your session has expired. Please login again to modify content.");
//					clientFactory.getEventBus().fireEvent(new LoginChangeEvent(LoginEvent.LOGOUT));								
//				} else {
//					ApplicationShell.setAuthenticationToken(response);
//				}
//			}
//			@Override
//			public void onFailure(ServerFailure error) {
//				// Ignore silently.
//			}
//		});
	}

	private static native String setPageLocationWithParam(String param) /*-{
		var baseUrl = $doc.location.href;
		var queryString = '';
		var hashString = '';

		// Pull off any hash.
		var i = baseUrl.indexOf('#');
		if (i != -1) {
			hashString = baseUrl.substring(i + 1);
			baseUrl = baseUrl.substring(0, i);
		}

		// Pull off any query string.
		i = baseUrl.indexOf('?');
		if (i != -1) {
			queryString = baseUrl.substring(i + 1);
			baseUrl = baseUrl.substring(0, i);
			
			// Pull off locale param if there already.
			i = queryString.indexOf('locale');
			if (i != -1) {
				queryString = queryString.substring(0, i - 1);
			}
		}

		// Ensure a final slash if non-empty.
		return baseUrl + '?' + queryString + '&' + param + '#' + hashString;
	}-*/;

	
	private static Boolean isUserAuthenticated = null;
	private static String authenticatedEmail = null;
	private static String authenticatedPassword = null;
	
	public static boolean isUserAuthenticated() {
		if (ApplicationShell.isUserAuthenticated == null) {
			ApplicationShell.isUserAuthenticated = Boolean.parseBoolean(Cookies.getCookie(IS_USER_AUTHENTICATED_KEY));
		}
		return ApplicationShell.isUserAuthenticated;
	}
	
	public static String getAuthenticatedEmail() {
		if (ApplicationShell.authenticatedEmail == null) {
			ApplicationShell.authenticatedEmail = Cookies.getCookie(AUTHENTICATED_EMAIL_KEY);
		}
		return ApplicationShell.authenticatedEmail;
	}

	public static String getAuthenticatedPassword() {
		if (ApplicationShell.authenticatedPassword == null) {
			ApplicationShell.authenticatedPassword = Cookies.getCookie(AUTHENTICATED_PASSWORD_KEY);
		}
		return ApplicationShell.authenticatedPassword;
	}

	public static void setAuthenticatedEmail(String authenticatedEmail) {
		Cookies.setCookie(AUTHENTICATED_EMAIL_KEY, authenticatedEmail, expireInOneYear());
		ApplicationShell.authenticatedEmail = authenticatedEmail;
	}

	public static void setAuthenticatedPassword(String authenticatedPassword) {
		Cookies.setCookie(AUTHENTICATED_PASSWORD_KEY, authenticatedPassword, expireInOneYear());
		ApplicationShell.authenticatedPassword = authenticatedPassword;
	}

	public static void setUserAuthenticated(boolean isUserAuthenticated) {
		Cookies.setCookie(IS_USER_AUTHENTICATED_KEY, "" + isUserAuthenticated, expireInOneYear());
		ApplicationShell.isUserAuthenticated = isUserAuthenticated;
	}

	private static Date expireInOneYear() {
		Date expires = new Date();
		expires.setTime(expires.getTime() + YEAR);
		return expires;
	}

	private void showLoginForm() {
		ApplicationShell.setUserAuthenticated(false);
		loginForm.removeStyleName("nodisplay");
		loginInfo.addStyleName("nodisplay");
		String email = ApplicationShell.getAuthenticatedEmail();
		if (email != null) {
			login.setEmail(email);
		}
	}

	private void hideLoginForm() {
		userEmail.setText(ApplicationShell.getAuthenticatedEmail());
		companyName.setText("RCC Elders Quorum Member");
		loginForm.addStyleName("nodisplay");
		loginInfo.removeStyleName("nodisplay");
	}

	@UiHandler("logoutLink")
	void onLogoutLinkClick(ClickEvent event) {
		event.preventDefault();
		clientFactory.getEventBus().fireEvent(new LoginChangeEvent(LoginEvent.LOGOUT));
	}

}
