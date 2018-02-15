package tk.burdukowsky.beauty_api.category;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("isAuthenticated()")
@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    <S extends Category> S save(S category);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    <S extends Category> Iterable<S> save(Iterable<S> categories);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Long categoryId);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Category category);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void delete(Iterable<? extends Category> categories);

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    void deleteAll();
}
