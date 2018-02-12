package tk.burdukowsky.beauty_api.company;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("isAuthenticated()")
@RepositoryRestResource(collectionResourceRel = "companies", path = "companies")
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {

    @RestResource(exported = false)
    @Override
    void delete(Long companyId);

    @PreAuthorize("hasRole('ADMIN') or @securityService.isCompanyOwner(authentication, #company)")
    @Override
    void delete(@Param("company") Company company);

    @RestResource(exported = false)
    @Override
    void delete(Iterable<? extends Company> companies);

    @RestResource(exported = false)
    @Override
    void deleteAll();
}
