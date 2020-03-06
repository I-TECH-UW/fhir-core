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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.model.base.PersistenceEntity;
import org.itech.fhircore.validation.annotation.ValidIdMap;
import org.itech.fhircore.validation.constraint.IdMapValidator.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "server_id", "resource_type" }) })
public class RemoteIdToLocalId extends PersistenceEntity<Long> {

	// persistence
	@Column(name = "resource_type", nullable = false, updatable = false)
	// validation
	@NotNull
	private ResourceType resourceType;

	// persistence
	@ManyToOne
	@JoinColumn(name = "server_id", nullable = false, updatable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	// validation
	@NotNull
	private Server remoteServer;

	// persistence
	@ElementCollection
	// validation
	@ValidIdMap(keyIdType = IdType.AlphaNum, valueIdType = IdType.AlphaNum)
	private Map<String, String> remoteIdToLocalIdMap;

	RemoteIdToLocalId() {

	}

	public RemoteIdToLocalId(Server server, ResourceType resourceType) {
		remoteIdToLocalIdMap = new HashMap<>();
		remoteServer = server;
		this.resourceType = resourceType;
	}


}
