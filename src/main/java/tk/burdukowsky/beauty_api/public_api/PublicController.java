package tk.burdukowsky.beauty_api.public_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.burdukowsky.beauty_api.company.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private CustomCompanyRepository customCompanyRepository;

    public PublicController(CustomCompanyRepository customCompanyRepository) {
        this.customCompanyRepository = customCompanyRepository;
    }

    @GetMapping("/companies-by-service-ids")
    public ResponseEntity<CustomPage<Company>> getCompaniesByServiceIds(
            @RequestParam(required = false, defaultValue = "0") long page,
            @RequestParam(required = false, defaultValue = "20") long limit,
            @RequestParam(required = false, defaultValue = "name") CompanySortField sort,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) CompanyType type,
            @RequestParam List<Long> ids) {
        return ResponseEntity.ok().body(customCompanyRepository.findAllByTypeAndProvidableServiceIds(
                type,
                ids,
                page,
                limit,
                sort,
                sortDirection));
    }

    @GetMapping("/companies-by-category-ids")
    public ResponseEntity<CustomPage<Company>> getCompaniesByCategoryIds(
            @RequestParam(required = false, defaultValue = "0") long page,
            @RequestParam(required = false, defaultValue = "20") long limit,
            @RequestParam(required = false, defaultValue = "name") CompanySortField sort,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) CompanyType type,
            @RequestParam List<Long> ids) {
        return ResponseEntity.ok().body(customCompanyRepository.findAllByTypeAndCategoryIds(
                type,
                ids,
                page,
                limit,
                sort,
                sortDirection));
    }
}
