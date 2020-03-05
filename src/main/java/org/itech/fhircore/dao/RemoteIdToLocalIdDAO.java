package org.itech.fhircore.dao;

import java.util.List;
import java.util.Optional;

import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.model.RemoteIdToLocalId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RemoteIdToLocalIdDAO extends CrudRepository<RemoteIdToLocalId, Long> {

	@Query("SELECT idMap FROM RemoteIdToLocalId idMap WHERE idMap.remoteServer.id = :serverId")
	List<RemoteIdToLocalId> findByServerId(@Param("serverId") Long serverId);

	@Query("SELECT idMap FROM RemoteIdToLocalId idMap WHERE idMap.remoteServer.id = :serverId AND idMap.resourceType = :resourceType")
	Optional<RemoteIdToLocalId> findByServerIdAndResourceType(@Param("serverId") Long serverId,
			@Param("resourceType") ResourceType resourceType);

}
