package com.edwardstlouis.client;

import com.edwardstlouis.client.request.RccRequestFactory;
import com.edwardstlouis.client.view.HomeView;
import com.edwardstlouis.client.view.impl.ApplicationShell;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	EventBus getEventBus();
	RccRequestFactory getRequestFactory();
	PlaceController getPlaceController();
	HomeView getHomeView();
	ApplicationShell getApplicationShell();
}
