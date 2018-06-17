package tk.burdukowsky.beauty_api.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

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

    @Override
    Iterable<Category> findAll(Sort sort);

    @Override
    Page<Category> findAll(Pageable pageable);

    @PreAuthorize("isAuthenticated()")
    @Override
    Category findOne(Long aLong);

    @PreAuthorize("isAuthenticated()")
    @Override
    boolean exists(Long aLong);

    @PreAuthorize("isAuthenticated()")
    @Override
    Iterable<Category> findAll();

    @PreAuthorize("isAuthenticated()")
    @Override
    Iterable<Category> findAll(Iterable<Long> longs);

    @PreAuthorize("isAuthenticated()")
    @Override
    long count();
}
