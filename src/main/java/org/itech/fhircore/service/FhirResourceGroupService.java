package org.itech.fhircore.service;

import java.util.List;
import java.util.Map;

import org.hl7.fhir.r4.model.ResourceType;

public interface FhirResourceGroupService {

	public enum FhirResourceCategories {
		All,
		// base resources
		Base, Individuals, Entities_1, Entities_2, Workflow, Management,
		// clinical resources
		Clinical, Summary, Diagnostics, Medications, Care_Provision, Request_Response
	}

	Map<FhirResourceCategories, List<ResourceType>> getFhirCategoriesToResourceTypes();

	Map<String, List<ResourceType>> getDefaultFhirGroupsToResourceTypes();

	Map<String, List<ResourceType>> getCustomFhirGroupsToResourceTypes();

	Map<String, List<ResourceType>> getAllFhirGroupsToResourceTypes();

}
