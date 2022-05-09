package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.services.helpers.Mapper;
import eu.elision.marketplace.web.dtos.CategoryDto;
import eu.elision.marketplace.web.dtos.CategoryMakeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriesController {
    private final Controller controller;

    @Autowired
    public CategoriesController(Controller controller) {
        this.controller = controller;
    }

    @GetMapping("/getCategories")
    ResponseEntity<List<CategoryDto>> getCategories() {
        final List<Category> allCategories = controller.findAllCategories();
        return ResponseEntity.ok(Mapper.toCategoryDtoList(allCategories));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/createCategory")
    ResponseEntity<String> createCategory(@RequestBody CategoryMakeDto categoryMakeDto) {
        controller.saveCategory(categoryMakeDto);
        return ResponseEntity.ok("{\"status\": \"ok\"}");
    }

}
