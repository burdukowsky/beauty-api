package tk.burdukowsky.beauty_api.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

    ApplicationUser findByEmail(String email);

    List<ApplicationUser> findAllByRolesNameOrderByFirstNameAscLastNameAsc(@Param("role") RoleEnum roleEnum);
}
