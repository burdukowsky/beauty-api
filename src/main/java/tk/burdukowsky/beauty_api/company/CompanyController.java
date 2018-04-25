package tk.burdukowsky.beauty_api.company;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import tk.burdukowsky.beauty_api.user.ApplicationUser;
import tk.burdukowsky.beauty_api.user.ApplicationUserRepository;

import java.util.*;

@RepositoryRestController
public class CompanyController {

    private ApplicationUserRepository applicationUserRepository;
    private CompanyRepository companyRepository;

    public CompanyController(ApplicationUserRepository applicationUserRepository,
                             CompanyRepository companyRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.companyRepository = companyRepository;
    }

    @GetMapping("/companies/my")
    public ResponseEntity<List<Company>> getCurrentUserCompanies(Authentication authentication) {
        ApplicationUser user = applicationUserRepository.findByEmail((String) authentication.getPrincipal());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(companyRepository.findAllByOwner_Id(user.getId()));
    }
}
