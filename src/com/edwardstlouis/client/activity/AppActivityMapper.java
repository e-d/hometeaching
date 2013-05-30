package com.edwardstlouis.client.activity;

import com.edwardstlouis.client.ClientFactory;
import com.edwardstlouis.client.place.HomePlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {
	
	private ClientFactory clientFactory;
	
	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof HomePlace) {
			return new HomeActivity((HomePlace) place, clientFactory);
		} 
		return null;
	}

}
