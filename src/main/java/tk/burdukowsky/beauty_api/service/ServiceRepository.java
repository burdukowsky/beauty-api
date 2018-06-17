package tk.burdukowsky.beauty_api.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "services", path = "services")
public interface ServiceRepository extends PagingAndSortingRepository<Service, Long> {

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    <S extends Service> S save(S service);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    <S extends Service> Iterable<S> save(Iterable<S> services);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long serviceId);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Service service);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Iterable<? extends Service> services);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void deleteAll();

    List<Service> findAllByCategory_IdOrderByNameAsc(@Param("id") long categoryId);
}
