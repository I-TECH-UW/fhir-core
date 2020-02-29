package org.itech.fhircore.service;

import java.net.URI;

import org.itech.fhircore.model.Server;

public interface ServerService extends CrudService<Server, Long> {

	Server saveNewServer(String identifier, String serverAddress);

	Server saveNewServer(String identifier, URI dataRequestUrl);

	void updateServerIdentifier(String oldIdentifier, String newIdentifier);


}
