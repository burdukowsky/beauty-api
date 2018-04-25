package tk.burdukowsky.beauty_api.account;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.burdukowsky.beauty_api.user.ApplicationUser;
import tk.burdukowsky.beauty_api.user.ApplicationUserRepository;

@RestController
@RequestMapping("/account")
public class AccountController {
    private ApplicationUserRepository applicationUserRepository;

    public AccountController(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ApplicationUser> getAccount(Authentication authentication) {
        ApplicationUser currentUser = applicationUserRepository.findByEmail((String) authentication.getPrincipal());
        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(currentUser);
    }
}
