package org.itech.fhircore.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.model.base.AuditableEntity;
import org.itech.fhircore.validation.annotation.ValidName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CustomFhirResourceGroup extends AuditableEntity<Long> {

	// validation
	@NotBlank
	@Size(min = 1, max = 255)
	@ValidName
	private String resourceGroupName;

	@ElementCollection
	private Set<ResourceType> resourceTypes;

	CustomFhirResourceGroup() {
	}

	public CustomFhirResourceGroup(String resourceGroupName) {
		this.resourceGroupName = resourceGroupName;
		resourceTypes = new HashSet<>();
	}

}
