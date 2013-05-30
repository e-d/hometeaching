package com.edwardstlouis.client.proxy;

import com.edwardstlouis.server.model.SampleModel;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


@ProxyFor(SampleModel.class)
public interface SampleModelProxy extends EntityProxy {

	String getId();

	String getName();

}
