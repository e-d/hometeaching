package com.edwardstlouis.client.view.impl;

import com.edwardstlouis.client.ClientFactory;
import com.edwardstlouis.client.view.HomeView;
import com.edwardstlouis.client.widgets.AssignmentWidget;
import com.edwardstlouis.client.widgets.DissolveAnimation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class HomeViewImpl extends Composite implements HomeView {
	
	interface MyUiBinder extends UiBinder<Widget, HomeViewImpl> {}
	
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private ClientFactory clientFactory;
	
	private Presenter presenter;
	
	@UiField HTMLPanel content;
	@UiField HTMLPanel applicationShell;
	@UiField FlowPanel contentHolder;
	@UiField HTMLPanel loadingMessage;
	@UiField HTMLPanel sectionName;
	
	public HomeViewImpl (ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onLoad() {
		applicationShell.add(clientFactory.getApplicationShell());
		
		// Make the menu fit best based on the user's browser window size.
		resizeMenu();
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				resizeMenu();
			}
		});

		super.onLoad();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	private void stopWaitSpinner() {
		loadingMessage.getElement().addClassName("nodisplay");
	}

	private void startWaitSpinner() {
		loadingMessage.getElement().removeClassName("nodisplay");
		contentHolder.clear();
	}

	private void resizeMenu() {
		int height = Window.getClientHeight();
		if (height > 340) {
			contentHolder.setHeight((height - 190) + "px");
		}
	}

	@Override
	public void setSection(final String section) {
		sectionName.clear();
		if ("contactUs".equals(section)) {
			sectionName.add(new HTML("Contact the Elders Quorumn Presidency"));
		} else if ("events".equals(section)) {
			sectionName.add(new HTML("Events and Announcements"));
		} else {
			sectionName.add(new HTML("Report on Your Visits"));
		}
		
		startWaitSpinner();
		Timer t = new Timer() {
			@Override
			public void run() {
				stopWaitSpinner();
				contentHolder.add(new AssignmentWidget(clientFactory, HomeViewImpl.this));
			}
		};
		t.schedule(200);
	}

	@Override
	public void removeMeAnimated(final Widget w) {
		// Update the color.
		w.removeStyleName("background-yellow");
		w.addStyleName("background-green");

		// Remove the widget.
		DissolveAnimation.fadeOut(w, 500);
		Timer t = new Timer() {
			@Override
			public void run() {
				contentHolder.remove(w);
			}
		};
		t.schedule(501);
	}


}
