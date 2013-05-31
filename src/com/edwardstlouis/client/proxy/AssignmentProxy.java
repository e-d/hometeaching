package com.edwardstlouis.client.proxy;

import com.edwardstlouis.server.model.Assignment;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


@ProxyFor(Assignment.class)
public interface AssignmentProxy extends EntityProxy {

	String getId();

	String getName();
	void setName(String name);

}
