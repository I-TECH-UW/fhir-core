package org.itech.fhircore.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.model.base.PersistenceEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "server_id", "resource_type" }) })
public class RemoteIdToLocalId extends PersistenceEntity<Long> {

	@Column(name = "resource_type")
	ResourceType resourceType;

	@ManyToOne
	@JoinColumn(name = "server_id", nullable = false, updatable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	Server remoteServer;

	@ElementCollection
	Map<String, String> remoteIdToLocalIdMap;

	RemoteIdToLocalId() {

	}

	public RemoteIdToLocalId(Server server, ResourceType resourceType) {
		remoteIdToLocalIdMap = new HashMap<>();
		remoteServer = server;
		this.resourceType = resourceType;
	}


}
