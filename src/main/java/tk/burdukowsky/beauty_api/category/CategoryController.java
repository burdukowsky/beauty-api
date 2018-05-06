package tk.burdukowsky.beauty_api.category;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories-with-services")
    public ResponseEntity<Iterable<Category>> getCategoriesWithServices() {
        Iterable<Category> categories = categoryRepository.findAll(new Sort(Direction.ASC, "name"));
        return ResponseEntity.ok().body(categories);
    }
}
