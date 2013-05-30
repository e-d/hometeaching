package com.edwardstlouis.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class LoginChangeEvent extends GwtEvent<LoginChangeEventHandler> {

	public enum LoginEvent {
		LOGOUT, LOGIN;
	}
	
	private LoginEvent event = null;
	
	public static Type<LoginChangeEventHandler> TYPE = new Type<LoginChangeEventHandler>();
	
	public LoginChangeEvent(LoginEvent event) {
		this.event = event;
	}
	
	public LoginEvent getLoginEvent() {
		return event;
	}
	
	@Override
	public Type<LoginChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginChangeEventHandler handler) {
		handler.onLoginChange(this);
	}

}
