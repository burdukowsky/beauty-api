package tk.burdukowsky.beauty_api.user;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tk.burdukowsky.beauty_api.utils.ApplicationUtils;

import javax.validation.Valid;

/**
 * Этот класс переопределяет стандартные методы POST, PUT и PATCH в ApplicationUserRepository
 */
@RepositoryRestController
public class ApplicationUserController {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private LocalValidatorFactoryBean beanValidator;

    public ApplicationUserController(ApplicationUserRepository applicationUserRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder,
                                     LocalValidatorFactoryBean beanValidator) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.beanValidator = beanValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(beanValidator);
    }

    @PostMapping("/users")
    public ResponseEntity<ApplicationUser> saveUser(@Valid @RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        ApplicationUser savedUser = applicationUserRepository.save(user);
        return ResponseEntity.ok().body(savedUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApplicationUser> replaceUser(@Valid @RequestBody ApplicationUser user,
                                                       @PathVariable("id") long id) {
        user.setId(id);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        ApplicationUser replacedUser = applicationUserRepository.save(user);
        return ResponseEntity.ok().body(replacedUser);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<ApplicationUser> updateUser(@RequestBody ApplicationUser user, @PathVariable("id") long id)
            throws Exception {
        ApplicationUser storedUser = applicationUserRepository.findOne(id);
        if (storedUser == null) {
            return ResponseEntity.badRequest().build();
        }
        user.setId(id);
        if (user.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        ApplicationUser updatedUser = applicationUserRepository
                .save(ApplicationUtils.merge(user, storedUser, ApplicationUser.class));
        return ResponseEntity.ok().body(updatedUser);
    }
}
