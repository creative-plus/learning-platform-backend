package ro.creativeplus.learningplatformbackend.mapper;

import org.springframework.stereotype.Component;
import ro.creativeplus.learningplatformbackend.dto.OrganizationResponseDto;
import ro.creativeplus.learningplatformbackend.model.Organization;

@Component
public class OrganizationMapper {

  public OrganizationResponseDto toDto(Organization organization) {
    OrganizationResponseDto dto = new OrganizationResponseDto();
    dto.setId(organization.getId());
    dto.setName(organization.getName());
    dto.setType(organization.getType());
    return dto;
  }
}
