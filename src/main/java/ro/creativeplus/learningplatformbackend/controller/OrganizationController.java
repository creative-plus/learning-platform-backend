package ro.creativeplus.learningplatformbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.creativeplus.learningplatformbackend.dto.OrganizationResponseDto;
import ro.creativeplus.learningplatformbackend.mapper.OrganizationMapper;
import ro.creativeplus.learningplatformbackend.model.Organization;
import ro.creativeplus.learningplatformbackend.service.OrganizationService;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/organizations")
@RolesAllowed("ROLE_TRAINER")
public class OrganizationController {

  private final OrganizationService organizationService;
  private final OrganizationMapper organizationMapper;

  OrganizationController(OrganizationService organizationService, OrganizationMapper organizationMapper) {
    this.organizationService = organizationService;
    this.organizationMapper = organizationMapper;
  }

  @GetMapping
  ResponseEntity<List<OrganizationResponseDto>> getOrganizations() {
    List<OrganizationResponseDto> dtos = organizationService.getOrganizations().stream()
        .map(organizationMapper::toDto)
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(dtos);
  }

  @GetMapping("/{id}")
  ResponseEntity<OrganizationResponseDto> getOrganization(@PathVariable int id) {
    Organization organization = organizationService.getOrganization(id);
    return ResponseEntity.ok().body(organizationMapper.toDto(organization));
  }
}
