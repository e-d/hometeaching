package com.edwardstlouis.client.widgets;

import com.edwardstlouis.client.ClientFactory;
import com.edwardstlouis.client.events.LoginChangeEvent;
import com.edwardstlouis.client.events.LoginChangeEvent.LoginEvent;
import com.edwardstlouis.client.view.impl.ApplicationShell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginWidget extends Composite {

	private static LoginWidgetUiBinder uiBinder = GWT.create(LoginWidgetUiBinder.class);

	interface LoginWidgetUiBinder extends UiBinder<Widget, LoginWidget> {
	}
	
	@UiField TextBox emailInput;
	@UiField PasswordTextBox passwordInput;
	@UiField Button loginSubmit;
	
	private ClientFactory clientFactory;

	public LoginWidget(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setEmail(String email) {
		emailInput.setText(email);
	}
	
	public String getEmail() {
		return emailInput.getText();
	}
	
	public String getPassword() {
		return passwordInput.getText();
	}

	@Override
	protected void onLoad() {
		// Add enter key handler onto email and password fields.
		KeyUpHandler enterHandler = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					onLoginSubmitClick(null);
				}
			}
		};
		emailInput.addKeyUpHandler(enterHandler);
		passwordInput.addKeyUpHandler(enterHandler);
		
		super.onLoad();
	}

	private void enableLogin() {
		// Add a slight delay so that Firefox doesn't send an enter event as soon as it is re-enabled.
		Timer t = new Timer() {
			@Override
			public void run() {
				loginSubmit.setEnabled(true);
			}
		};
		t.schedule(500);
	}
	
	private void disableLogin() {
		loginSubmit.setEnabled(false);
	}
	
	private boolean isLoginEnabled() {
		return loginSubmit.isEnabled();
	}

	@UiHandler("loginSubmit")
	void onLoginSubmitClick(ClickEvent event) {
		if (!isLoginEnabled()) {
			return;
		}
		
		clientFactory.getEventBus().fireEvent(new LoginChangeEvent(LoginEvent.LOGIN));
		ApplicationShell.setUserAuthenticated(true);
		ApplicationShell.setAuthenticatedEmail(emailInput.getText());
		ApplicationShell.setAuthenticatedPassword(passwordInput.getText());
//		disableLogin();
//		clientFactory.getRequestFactory().userControllerRequest().authenticateUser(emailInput.getText(), passwordInput.getText()).fire(new Receiver<UserProxy>() {
//			@Override
//			public void onSuccess(UserProxy response) {
//				if (response.isAuthenticated()) {
//					// Add the user info
//					ApplicationShell.setUserAuthenticated(true);
//					ApplicationShell.setXimaEmployee(response.isXimaEmployee());
//					ApplicationShell.setChronicallTranslator(response.isChronicallTranslator());
//					ApplicationShell.setAuthenticatedCompany(response.getCompany());
//					ApplicationShell.setAuthenticatedEmail(response.getEmail());
//					ApplicationShell.setAuthenticatedPassword(response.getPassword());
//					ApplicationShell.setAuthenticationToken(response.getAuthenticationToken());
//					if (ApplicationShell.isXimaEmployee()) {
//						clientFactory.getEventBus().fireEvent(new LoginChangeEvent(LoginEvent.LOGIN_XIMA));
//						Util.recordAnalyticsEvent(EventCategoryType.GWT_SUPPORT_SITE, EventActionType.LOGIN_XIMA, response.getEmail());
//					} else {
//						clientFactory.getEventBus().fireEvent(new LoginChangeEvent(LoginEvent.LOGIN_PARTNER));
//						Util.recordAnalyticsEvent(EventCategoryType.GWT_SUPPORT_SITE, EventActionType.LOGIN_PARTNER, response.getEmail());
//					}
//				} else {
//					Util.recordAnalyticsEvent(EventCategoryType.GWT_SUPPORT_SITE, EventActionType.LOGIN_FAIL, emailInput.getText());
//					Window.alert("Invalid email and/or password. Please try again.");
//				}
//				enableLogin();
//			}
//			
//			@Override
//			public void onFailure(ServerFailure error) {
//				enableLogin();
//				Util.recordAnalyticsEvent(EventCategoryType.GWT_SUPPORT_SITE, EventActionType.LOGIN_FAIL, emailInput.getText());
//				super.onFailure(error);
//			}
//		});
	}
}
