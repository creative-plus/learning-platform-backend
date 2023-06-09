package ro.creativeplus.learningplatformbackend.service;

import org.springframework.stereotype.Service;
import ro.creativeplus.learningplatformbackend.exception.ObjectAlreadyExistsException;
import ro.creativeplus.learningplatformbackend.exception.ObjectNotFoundException;
import ro.creativeplus.learningplatformbackend.model.Organization;
import ro.creativeplus.learningplatformbackend.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

  private OrganizationRepository organizationRepository;

  OrganizationService(OrganizationRepository organizationRepository) {
    this.organizationRepository = organizationRepository;
  }

  public List<Organization> getOrganizations() {
    return organizationRepository.findAll();
  }

  public Organization getOrganization(int id) {
    Optional<Organization> organization = organizationRepository.findById(id);
    if(organization.isEmpty()) {
      throw new ObjectNotFoundException("");
    }
    return organization.get();
  }

  public Organization addPOrganization(Organization organization) {
    if(organization.getId() > 0) {
      Optional<Organization> existingOrganization = organizationRepository.findById(organization.getId());
      if(existingOrganization.isPresent()) {
        throw new ObjectAlreadyExistsException("");
      }
    }
    return organizationRepository.save(organization);
  }

  public Organization editOrganization(Organization organization) {
    if(organization.getId() == 0) {
      throw new ObjectNotFoundException("");
    }
    Optional<Organization> existingOrganization = organizationRepository.findById(organization.getId());
    if(existingOrganization.isEmpty()) {
      throw new ObjectNotFoundException("");
    }

    return organizationRepository.save(organization);
  }

  public void deleteOrganization(Organization organization) {
    organizationRepository.delete(organization);
  }

  public void deleteOrganizationById(int id) {
    Organization organization = this.getOrganization(id);
    this.deleteOrganization(organization);
  }

}
