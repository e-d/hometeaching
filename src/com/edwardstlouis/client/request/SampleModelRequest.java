package com.edwardstlouis.client.request;

import com.edwardstlouis.client.proxy.SampleModelProxy;
import com.edwardstlouis.server.model.SampleModel;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


@Service(SampleModel.class)
public interface SampleModelRequest extends RequestContext {

	Request<SampleModelProxy> findSampleModel(String id);
	
}
