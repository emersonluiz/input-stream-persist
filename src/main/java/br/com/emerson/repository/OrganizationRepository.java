package br.com.emerson.repository;

import javax.inject.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.emerson.model.Organization;

@Named
public interface OrganizationRepository extends JpaRepository<Organization, Integer>{

}
