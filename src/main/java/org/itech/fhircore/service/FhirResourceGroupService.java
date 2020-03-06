package org.itech.fhircore.service;

import java.util.Map;
import java.util.Set;

import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.model.CustomFhirResourceGroup;

public interface FhirResourceGroupService {

	public enum FhirResourceCategories {
		All,
		// base resources
		Base, Individuals, Entities_1, Entities_2, Workflow, Management,
		// clinical resources
		Clinical, Summary, Diagnostics, Medications, Care_Provision, Request_Response
	}

	Map<FhirResourceCategories, Set<ResourceType>> getFhirCategoriesToResourceTypes();

	Map<String, Set<ResourceType>> getDefaultFhirGroupsToResourceTypes();

	Map<String, Set<ResourceType>> getCustomFhirGroupsToResourceTypes();

	Map<String, Set<ResourceType>> getAllFhirGroupsToResourceTypes();

	CustomFhirResourceGroup createFhirResourceGroup(String resourceGroupName, Set<ResourceType> resourceTypes);

}
