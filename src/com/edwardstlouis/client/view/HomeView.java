package com.edwardstlouis.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public interface HomeView extends IsWidget {
	
	void setPresenter(Presenter presenter);
	
	public interface Presenter {
		void goTo(Place place);
	}

	void setSection(String section);

	void removeMeAnimated(Widget w);
}
