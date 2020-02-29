package org.itech.fhircore.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.model.base.AuditableEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CustomFhirResourceGroup extends AuditableEntity<Long> {

	@Size(min = 1, max = 255)
	private String resourceGroupName;

	@ElementCollection
	List<ResourceType> resourceTypes;

}
