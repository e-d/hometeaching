package com.edwardstlouis.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class HomePlace extends Place {

	private String section;

	public HomePlace(String token) {
		this.section = token;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
	
	@Prefix("home")
	public static class Tokenizer implements PlaceTokenizer<HomePlace> {

		@Override
		public HomePlace getPlace(String token) {
			return new HomePlace(token);
		}

		@Override
		public String getToken(HomePlace place) {
			return place.getSection();
		}
		
	}
	
}
