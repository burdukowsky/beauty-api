package tk.burdukowsky.beauty_api.metric;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.burdukowsky.beauty_api.company.CompanyRepository;
import tk.burdukowsky.beauty_api.user.ApplicationUser;
import tk.burdukowsky.beauty_api.user.ApplicationUserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/metrics")
public class MetricController {

    private ApplicationUserRepository applicationUserRepository;
    private CompanyRepository companyRepository;

    public MetricController(ApplicationUserRepository applicationUserRepository, CompanyRepository companyRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.companyRepository = companyRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> getAdminMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("usersCount", applicationUserRepository.count());
        metrics.put("companiesCount", companyRepository.count());
        return ResponseEntity.ok().body(metrics);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/member")
    public ResponseEntity<Map<String, Object>> getMemberMetrics(Authentication authentication) {
        ApplicationUser currentUser = applicationUserRepository.findByEmail((String) authentication.getPrincipal());
        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("companiesCount", companyRepository.countAllByOwner_Id(currentUser.getId()));
        return ResponseEntity.ok().body(metrics);
    }
}
