package com.edwardstlouis.client;

import com.edwardstlouis.client.request.RccRequestFactory;
import com.edwardstlouis.client.view.HomeView;
import com.edwardstlouis.client.view.impl.ApplicationShell;
import com.edwardstlouis.client.view.impl.HomeViewImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {
	
	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final RccRequestFactory requestFactory = GWT.create(RccRequestFactory.class);
	private final HomeViewImpl homeViewImpl = new HomeViewImpl(this);
	private final ApplicationShell applicationShell = new ApplicationShell(this);
	
	public ClientFactoryImpl() {
		requestFactory.initialize(eventBus);
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public HomeView getHomeView() {
		return homeViewImpl;
	}

	@Override
	public RccRequestFactory getRequestFactory() {
		return requestFactory;
	}

	@Override
	public ApplicationShell getApplicationShell() {
		return applicationShell;
	}

}
