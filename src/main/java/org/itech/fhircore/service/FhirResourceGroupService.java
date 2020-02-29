package org.itech.fhircore.service;

import java.util.List;
import java.util.Map;

import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.service.impl.FhirResourceGroupServiceImpl.FhirResourceCategories;

public interface FhirResourceGroupService {

	Map<FhirResourceCategories, List<ResourceType>> getFhirCategoriesToResourceTypes();

	Map<String, List<ResourceType>> getDefaultFhirGroupsToResourceTypes();

	Map<String, List<ResourceType>> getCustomFhirGroupsToResourceTypes();

	Map<String, List<ResourceType>> getAllFhirGroupsToResourceTypes();

}
