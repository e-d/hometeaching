package com.edwardstlouis.client.widgets;

import static com.edwardstlouis.client.view.impl.ApplicationShell.getAuthenticatedEmail;
import static com.edwardstlouis.client.view.impl.ApplicationShell.getAuthenticatedPassword;
import static com.edwardstlouis.client.view.impl.ApplicationShell.isUserAuthenticated;

import com.edwardstlouis.client.ClientFactory;
import com.edwardstlouis.client.events.LoginChangeEvent;
import com.edwardstlouis.client.events.LoginChangeEvent.LoginEvent;
import com.edwardstlouis.client.proxy.AssignmentProxy;
import com.edwardstlouis.client.request.AssignmentRequest;
import com.edwardstlouis.client.view.HomeView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class AssignmentWidget extends Composite {

	private static AssignmentWidgetUiBinder uiBinder = GWT.create(AssignmentWidgetUiBinder.class);
	
	private AssignmentProxy t;
	private ClientFactory clientFactory;
	private HomeView parent;
	
	@UiField HTMLPanel widget;
	@UiField TextArea comments;
	@UiField Button saveButton;
	@UiField HTML family;
	@UiField CheckBox visited;


	interface AssignmentWidgetUiBinder extends UiBinder<Widget, AssignmentWidget> {
	}

	public AssignmentWidget(ClientFactory clientFactory, HomeView parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.clientFactory = clientFactory;
		this.parent = parent;
		
		// Center the save button.
		saveButton.getElement().getParentElement().getStyle().setTextAlign(TextAlign.CENTER);
		
		// Default to yellow.
		widget.addStyleName("background-yellow");
		renewAssignmentRequest();
	}
	
	@Override
	protected void onLoad() {
	}
	
	public AssignmentWidget(ClientFactory clientFactory, HomeView parent, AssignmentProxy t) {
		this(clientFactory, parent);
		setAssignment(t);
	}
	
	public void setAssignment(AssignmentProxy t) {
		this.t = t;
		renewAssignmentRequest();
		family.setText("St Louis, Ed and Laura");
		visited.setValue(false);
		comments.setValue("");
		
		
	}
	
	// The request must be stored because we need to use the exact same request to do future interactions with TrainingUserProxy or we will get errors.
	private AssignmentRequest assignmentRequest;
	private void renewAssignmentRequest() {
		assignmentRequest = clientFactory.getRequestFactory().assignmentRequest();
	}
	
	private AssignmentProxy getEditableAssignmentProxy() {
		return assignmentRequest.edit(t);
	}

	@UiHandler("saveButton")
	public void onSaveClick(ClickEvent e) {
		if (isUserAuthenticated()) {
			AssignmentProxy editableT = getEditableAssignmentProxy();
			editableT.setName(comments.getValue());

			assignmentRequest.updateAssignment(editableT, getAuthenticatedEmail(), getAuthenticatedPassword()).fire(new Receiver<Void>() {
				@Override
				public void onSuccess(Void response) {
					renewAssignmentRequest();
					saveButton.setHTML("Update");
					parent.removeMeAnimated(AssignmentWidget.this);
				}

				@Override
				public void onFailure(ServerFailure error) {
					if (error.getMessage().contains("401")) {
						clientFactory.getEventBus().fireEvent(new LoginChangeEvent(LoginEvent.LOGOUT));
						Window.Location.reload();
					} else {
						Window.alert("Saving Error: " + error.getMessage());
					}
				}
			});
		}
	}
	
}
