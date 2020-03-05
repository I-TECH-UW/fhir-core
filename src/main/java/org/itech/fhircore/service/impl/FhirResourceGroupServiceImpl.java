package org.itech.fhircore.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.dao.CustomFhirResourceGroupDAO;
import org.itech.fhircore.model.CustomFhirResourceGroup;
import org.itech.fhircore.service.FhirResourceGroupService;
import org.springframework.stereotype.Service;

@Service
public class FhirResourceGroupServiceImpl implements FhirResourceGroupService {

	private final Map<FhirResourceCategories, Set<ResourceType>> fhirCategoriesToResourceTypes;
	private final Map<String, Set<ResourceType>> defaultFhirGroupsToResourceTypes;
	private final Map<String, Set<ResourceType>> customFhirGroupsToResourceTypes;

	public FhirResourceGroupServiceImpl(CustomFhirResourceGroupDAO customFhirResourceGroupDAO) {
		fhirCategoriesToResourceTypes = new HashMap<>();

		Set<ResourceType> entity1Entries = new HashSet<>();
		entity1Entries.add(ResourceType.Organization);
		entity1Entries.add(ResourceType.OrganizationAffiliation);
		entity1Entries.add(ResourceType.HealthcareService);
		entity1Entries.add(ResourceType.Endpoint);
		entity1Entries.add(ResourceType.Location);
		fhirCategoriesToResourceTypes.put(FhirResourceCategories.Entities_1, entity1Entries);

		Set<ResourceType> workflowEntries = new HashSet<>();
		workflowEntries.add(ResourceType.Task);
		workflowEntries.add(ResourceType.Appointment);
		workflowEntries.add(ResourceType.AppointmentResponse);
		workflowEntries.add(ResourceType.Schedule);
		workflowEntries.add(ResourceType.VerificationResult);
		fhirCategoriesToResourceTypes.put(FhirResourceCategories.Workflow, workflowEntries);

		fhirCategoriesToResourceTypes.put(FhirResourceCategories.Base,
				Stream.concat(entity1Entries.stream(), workflowEntries.stream()).collect(Collectors.toSet()));

		// put all fhirCategoriesToResourceTypes into the all type
		Set<ResourceType> allEntries = new HashSet<>();
		for (Set<ResourceType> resourceTypeList : fhirCategoriesToResourceTypes.values()
				) {
			allEntries.addAll(resourceTypeList);
		}
		fhirCategoriesToResourceTypes.put(FhirResourceCategories.All, allEntries);

		// get map that uses strings instead of FhirResourceCategories
		defaultFhirGroupsToResourceTypes = new HashMap<>();
		for (Entry<FhirResourceCategories, Set<ResourceType>> fhirCategory : fhirCategoriesToResourceTypes
				.entrySet()) {
			defaultFhirGroupsToResourceTypes.put(fhirCategory.getKey().name(), fhirCategory.getValue());
		}


		// add any custom FhirResourceGroups
		customFhirGroupsToResourceTypes = new HashMap<>();
		for (CustomFhirResourceGroup customFhirResourceGroup : customFhirResourceGroupDAO.findAll()) {
			customFhirGroupsToResourceTypes.put(customFhirResourceGroup.getResourceGroupName(),
					customFhirResourceGroup.getResourceTypes());
		}

	}

	@Override
	public Map<FhirResourceCategories, List<ResourceType>> getFhirCategoriesToResourceTypes() {
		return fhirCategoriesToResourceTypes.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> List.copyOf(e.getValue())));
	}

	@Override
	public Map<String, List<ResourceType>> getDefaultFhirGroupsToResourceTypes() {
		return defaultFhirGroupsToResourceTypes.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> List.copyOf(e.getValue())));
	}

	@Override
	public Map<String, List<ResourceType>> getCustomFhirGroupsToResourceTypes() {
		return customFhirGroupsToResourceTypes.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> List.copyOf(e.getValue())));
	}

	@Override
	public Map<String, List<ResourceType>> getAllFhirGroupsToResourceTypes() {
		Map<String, List<ResourceType>> allFhirGroupsToResourceTypes = new HashMap<>();
		allFhirGroupsToResourceTypes.putAll(getDefaultFhirGroupsToResourceTypes());
		allFhirGroupsToResourceTypes.putAll(getCustomFhirGroupsToResourceTypes());
		return allFhirGroupsToResourceTypes;
	}

}
