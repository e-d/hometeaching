package com.edwardstlouis.client.activity;

import com.edwardstlouis.client.ClientFactory;
import com.edwardstlouis.client.place.HomePlace;
import com.edwardstlouis.client.view.HomeView;
import com.edwardstlouis.client.view.HomeView.Presenter;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class HomeActivity extends AbstractActivity implements Presenter {

	private ClientFactory clientFactory;
	private String section;
	
	public HomeActivity(HomePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.section = place.getSection();
	}

	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		HomeView homeView = clientFactory.getHomeView();
		homeView.setPresenter(this);
		containerWidget.setWidget(homeView.asWidget());
		homeView.setSection(section);
		clientFactory.getApplicationShell().setActiveLink(section);
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
