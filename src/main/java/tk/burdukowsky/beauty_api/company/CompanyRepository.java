package tk.burdukowsky.beauty_api.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "companies", path = "companies")
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {

    // неэкспортируемые методы НАЧАЛО

    @RestResource(exported = false)
    @Override
    Iterable<Company> findAll(Sort sort);

    @RestResource(exported = false)
    @Override
    boolean exists(Long companyId);

    @RestResource(exported = false)
    @Override
    Iterable<Company> findAll();

    @RestResource(exported = false)
    @Override
    Iterable<Company> findAll(Iterable<Long> companyIds);

    @RestResource(exported = false)
    @Override
    long count();

    @RestResource(exported = false)
    @Override
    <S extends Company> Iterable<S> save(Iterable<S> companies);

    @RestResource(exported = false)
    @Override
    void delete(Long companyId);

    @RestResource(exported = false)
    @Override
    void delete(Iterable<? extends Company> companies);

    @RestResource(exported = false)
    @Override
    void deleteAll();

    @PreAuthorize("hasRole('ADMIN') or @securityService.isUserIdEquals(authentication, #id)")
    @RestResource(exported = false)
    long countAllByOwner_Id(@Param("id") long ownerId);

    @PreAuthorize("hasRole('ADMIN')")
    @RestResource(exported = false)
    @Transactional
    @Modifying
    @Query("update Company c set c.rating = :rating where c.id = :companyId")
    int setRatingFor(@Param("rating") Float rating, @Param("companyId") long companyId);

    @PreAuthorize("isAuthenticated()")
    @RestResource(exported = false)
    @Transactional
    @Modifying
    @Query("update Company c set c.image = :image where c.id = :companyId")
    int setImageFor(@Param("image") String image, @Param("companyId") long companyId);

    // неэкспортируемые методы КОНЕЦ

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    Page<Company> findAll(Pageable pageable);

    @Override
    Company findOne(Long companyId);

    @PreAuthorize("hasRole('ADMIN') or @securityService.isCompanyOwner(authentication, #company)")
    @Override
    <S extends Company> S save(@Param("company") S company);

    @PreAuthorize("hasRole('ADMIN') or @securityService.isCompanyOwner(authentication, #company)")
    @Override
    void delete(@Param("company") Company company);

    @PreAuthorize("hasRole('ADMIN') or @securityService.isUserIdEquals(authentication, #id)")
    List<Company> findAllByOwner_IdOrderByNameAsc(@Param("id") long ownerId);
}
