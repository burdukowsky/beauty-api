package tk.burdukowsky.beauty_api.company;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RepositoryRestController
public class CompanyRepositoryRestController {

    private CompanyRepository companyRepository;
    private LocalValidatorFactoryBean beanValidator;

    public CompanyRepositoryRestController(CompanyRepository companyRepository, LocalValidatorFactoryBean beanValidator) {
        this.companyRepository = companyRepository;
        this.beanValidator = beanValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(beanValidator);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/companies/{id}/rating")
    public ResponseEntity<Void> getCompaniesByServiceIds(@PathVariable("id") long companyId,
                                                         @RequestBody @Valid RatingWrapper rating) {
        if (companyRepository.setRatingFor(rating.getValue(), companyId) > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
