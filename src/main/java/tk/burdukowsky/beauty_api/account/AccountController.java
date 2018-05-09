package tk.burdukowsky.beauty_api.account;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tk.burdukowsky.beauty_api.user.ApplicationUser;
import tk.burdukowsky.beauty_api.user.ApplicationUserRepository;
import tk.burdukowsky.beauty_api.utils.ApplicationUtils;

@RestController
@RequestMapping("/account")
public class AccountController {
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountController(ApplicationUserRepository applicationUserRepository,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public ResponseEntity<ApplicationUser> getAccount(Authentication authentication) {
        ApplicationUser currentUser = applicationUserRepository.findByEmail((String) authentication.getPrincipal());
        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(currentUser);
    }

    @PatchMapping
    public ResponseEntity<ApplicationUser> updateAccount(@RequestBody ApplicationUser user,
                                                         Authentication authentication) throws Exception {
        ApplicationUser currentUser = applicationUserRepository.findByEmail((String) authentication.getPrincipal());
        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }
        user.setId(currentUser.getId());
        if (user.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        ApplicationUser updatedUser = applicationUserRepository
                .save(ApplicationUtils.merge(user, currentUser, ApplicationUser.class));
        return ResponseEntity.ok().body(updatedUser);
    }
}
