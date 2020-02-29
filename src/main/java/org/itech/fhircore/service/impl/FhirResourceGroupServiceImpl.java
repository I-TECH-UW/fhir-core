package org.itech.fhircore.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.dao.CustomFhirResourceGroupDAO;
import org.itech.fhircore.model.CustomFhirResourceGroup;
import org.itech.fhircore.service.FhirResourceGroupService;
import org.springframework.stereotype.Service;

@Service
public class FhirResourceGroupServiceImpl implements FhirResourceGroupService {

	public enum FhirResourceCategories {
		// base resources
		Base, Individuals, Entities_1, Entities_2, Workflow, Management,
		// clinical resources
		Clinical, Summary, Diagnostics, Medications, Care_Provision, Request_Response;
	}

	private final Map<FhirResourceCategories, List<ResourceType>> fhirCategoriesToResourceTypes;
	private final Map<String, List<ResourceType>> defaultFhirGroupsToResourceTypes;
	private final Map<String, List<ResourceType>> customFhirGroupsToResourceTypes;

	public FhirResourceGroupServiceImpl(CustomFhirResourceGroupDAO customFhirResourceGroupDAO) {
		fhirCategoriesToResourceTypes = new HashMap<>();

		List<ResourceType> entity1Entries = new ArrayList<>();
		entity1Entries.add(ResourceType.Organization);
		entity1Entries.add(ResourceType.OrganizationAffiliation);
		entity1Entries.add(ResourceType.HealthcareService);
		entity1Entries.add(ResourceType.Endpoint);
		entity1Entries.add(ResourceType.Location);
		fhirCategoriesToResourceTypes.put(FhirResourceCategories.Entities_1, entity1Entries);

		List<ResourceType> workflowEntries = new ArrayList<>();
		workflowEntries.add(ResourceType.Task);
		workflowEntries.add(ResourceType.Appointment);
		workflowEntries.add(ResourceType.AppointmentResponse);
		workflowEntries.add(ResourceType.Schedule);
		workflowEntries.add(ResourceType.VerificationResult);
		fhirCategoriesToResourceTypes.put(FhirResourceCategories.Workflow, workflowEntries);

		fhirCategoriesToResourceTypes.put(FhirResourceCategories.Base,
				Stream.concat(entity1Entries.stream(), workflowEntries.stream()).collect(Collectors.toList()));

		defaultFhirGroupsToResourceTypes = new HashMap<>();
		for (Entry<FhirResourceCategories, List<ResourceType>> fhirCategory : fhirCategoriesToResourceTypes
				.entrySet()) {
			defaultFhirGroupsToResourceTypes.put(fhirCategory.getKey().name(), fhirCategory.getValue());
		}

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
