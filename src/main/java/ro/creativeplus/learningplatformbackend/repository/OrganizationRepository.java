package ro.creativeplus.learningplatformbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.creativeplus.learningplatformbackend.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
