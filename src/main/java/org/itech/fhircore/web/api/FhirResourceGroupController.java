package org.itech.fhircore.web.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.hl7.fhir.r4.model.ResourceType;
import org.itech.fhircore.model.CustomFhirResourceGroup;
import org.itech.fhircore.service.FhirResourceGroupService;
import org.itech.fhircore.web.dto.CustomFhirResourceGroupDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fhirResourceGroups")
public class FhirResourceGroupController {

	private FhirResourceGroupService fhirResourceGroupService;

	public FhirResourceGroupController(FhirResourceGroupService fhirResourceGroupService) {
		this.fhirResourceGroupService = fhirResourceGroupService;
	}

	@GetMapping
	public ResponseEntity<Map<String, Set<ResourceType>>> getAllFhirResourceGroups() {
		return ResponseEntity.ok(fhirResourceGroupService.getAllFhirGroupsToResourceTypes());
	}

	@GetMapping("/custom")
	public ResponseEntity<Map<String, Set<ResourceType>>> getCustomFhirResourceGroups() {
		return ResponseEntity.ok(fhirResourceGroupService.getCustomFhirGroupsToResourceTypes());
	}

	@GetMapping("/default")
	public ResponseEntity<Map<String, Set<ResourceType>>> getDefaultFhirResourceGroups() {
		return ResponseEntity.ok(fhirResourceGroupService.getDefaultFhirGroupsToResourceTypes());
	}

	@PostMapping("/custom")
	public ResponseEntity<CustomFhirResourceGroup> createCustomFhirResourceGroup(
			@RequestBody @Valid CustomFhirResourceGroupDTO dto) throws URISyntaxException {
		CustomFhirResourceGroup resourceGroup = fhirResourceGroupService
				.createFhirResourceGroup(dto.getResourceGroupName(), dto.getResourceTypes());
		return ResponseEntity.created(new URI("/fhirResourceGroups/custom/" + resourceGroup.getId()))
				.body(resourceGroup);
	}

}
