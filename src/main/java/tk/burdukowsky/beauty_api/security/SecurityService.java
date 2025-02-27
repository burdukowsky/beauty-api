package tk.burdukowsky.beauty_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tk.burdukowsky.beauty_api.company.Company;
import tk.burdukowsky.beauty_api.user.ApplicationUser;
import tk.burdukowsky.beauty_api.user.ApplicationUserRepository;

@Component("securityService")
public class SecurityService {

    private final ApplicationUserRepository applicationUserRepository;

    public SecurityService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    public boolean isCompanyOwner(Authentication authentication, Company company) {
        if (company == null) {
            return true;
        }
        ApplicationUser user = applicationUserRepository.findByEmail((String) authentication.getPrincipal());
        if (user == null) {
            return false;
        }
        return user.getId() == company.getOwner().getId();
    }

    public boolean isUserIdEquals(Authentication authentication, long userId) {
        ApplicationUser user = applicationUserRepository.findByEmail((String) authentication.getPrincipal());
        if (user == null) {
            return false;
        }
        return user.getId() == userId;
    }
}
