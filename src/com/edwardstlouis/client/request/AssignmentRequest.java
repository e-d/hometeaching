package com.edwardstlouis.client.request;

import com.edwardstlouis.client.proxy.AssignmentProxy;
import com.edwardstlouis.server.model.Assignment;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


@Service(Assignment.class)
public interface AssignmentRequest extends RequestContext {

	Request<AssignmentProxy> findAssignment(String id);
	Request<Void> updateAssignment(AssignmentProxy assignment, String email, String passwordHash);

}
