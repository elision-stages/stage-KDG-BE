package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.web.dtos.ResponseDto;
import eu.elision.marketplace.web.dtos.category.CategoryDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to handle calls about categories
 */
@RestController
@RequestMapping("category")
public class CategoriesController {
    private final Controller controller;

    /**
     * Public constructor
     *
     * @param controller the controller that the category controller has to use
     */
    @Autowired
    public CategoriesController(Controller controller) {
        this.controller = controller;
    }

    @GetMapping
    ResponseEntity<List<CategoryDto>> getCategories() {
        final List<Category> allCategories = controller.findAllCategories();
        return ResponseEntity.ok(Mapper.toCategoryDtoList(allCategories));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/create")
    ResponseEntity<ResponseDto> createCategory(@RequestBody CategoryMakeDto categoryMakeDto) {
        controller.saveCategory(categoryMakeDto);
        return ResponseEntity.ok(new ResponseDto("success"));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("edit")
    ResponseEntity<ResponseDto> editCategory(@RequestBody CategoryDto editCategoryDto) {
        controller.editCategory(editCategoryDto);
        return ResponseEntity.ok(new ResponseDto("success"));
    }

}
