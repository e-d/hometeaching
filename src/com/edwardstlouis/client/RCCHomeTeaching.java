package com.edwardstlouis.client;

import com.edwardstlouis.client.activity.AppActivityMapper;
import com.edwardstlouis.client.place.AppPlaceHistoryMapper;
import com.edwardstlouis.client.place.HomePlace;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RCCHomeTeaching implements EntryPoint {
	private Place defaultPlace = new HomePlace("visits");
	private SimplePanel appWidget = new SimplePanel();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		
		// Start ActivityManager
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(appWidget);
		
		// Start PlacehistoryHandler
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		// Go to the default place.
		RootPanel.get().add(appWidget);
		historyHandler.handleCurrentHistory();
	}
}
