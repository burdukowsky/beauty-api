package tk.burdukowsky.beauty_api.company;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.burdukowsky.beauty_api.user.ApplicationUser;
import tk.burdukowsky.beauty_api.user.ApplicationUserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CompanyController {

    private Cloudinary cloudinary;
    private CompanyRepository companyRepository;
    private ApplicationUserRepository applicationUserRepository;

    public CompanyController(Cloudinary cloudinary,
                             CompanyRepository companyRepository,
                             ApplicationUserRepository applicationUserRepository) {
        this.cloudinary = cloudinary;
        this.companyRepository = companyRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/companies/{id}/image")
    public ResponseEntity<Map<String, Object>> updateCompanyImage(@PathVariable("id") long id,
                                                                  @RequestParam("image") MultipartFile image,
                                                                  HttpServletRequest request,
                                                                  Principal principal) {
        ApplicationUser applicationUser = null;
        Map<String, Object> response = new HashMap<>();

        if (!request.isUserInRole("ADMIN")) {
            applicationUser = applicationUserRepository.findByEmail(principal.getName());
            if (applicationUser == null) {
                response.put("message", "User deleted");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
        }

        Company company = companyRepository.findOne(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }

        if (applicationUser != null && (company.getOwner().getId() != applicationUser.getId())) {
            response.put("message", "User is not the company owner");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        try {
            byte[] imageBytes = image.getBytes();
            Uploader uploader = cloudinary.uploader();
            Map uploadResult = uploader.upload(imageBytes, ObjectUtils.asMap(
                    "transformation", new Transformation().crop("limit").width(400).height(400)));
            String version = String.valueOf(uploadResult.get("version"));
            String publicId = String.valueOf(uploadResult.get("public_id"));
            String format = String.valueOf(uploadResult.get("format"));
            String imagePath = version + "/" + publicId + "." + format;
            if (companyRepository.setImageFor(imagePath, id) > 0) {
                response.put("image", imagePath);
                return ResponseEntity.ok().body(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.put("message", "Error uploading file");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
