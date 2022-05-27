package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.logic.services.product.CategoryService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoriesControllerTest {

    private static URL base;
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private Integer port;
    @Autowired
    private Controller controller;
    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user", "password");
        base = new URL(String.format("http://localhost:%s", port));
    }

    @Test
    void getCategories() {
        ResponseEntity<String> response = restTemplate.getForEntity(String.format("%s/%s", base, "category"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getFakeCategory() {
        ResponseEntity<String> response = restTemplate.getForEntity(String.format("%s/%s/%d", base, "category", -1), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void getCategory() {
        Category category = new Category();
        category.setId(123L);
        category.setName("nice");
        categoryService.save(category);
        ResponseEntity<String> response = restTemplate.getForEntity(String.format("%s/%s/%d", base, "category", 123), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    //@Test
    void createCategory() {
        Category category = new Category();

        String name = RandomStringUtils.randomAlphabetic(5);
        int initSize = controller.findAllCategories().size();

        category.setName(name);

        ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/%s", base, "category/create"), category, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(controller.findAllCategories()).hasSize(initSize + 1);

        Category repoCategory = controller.findCategoryByName(name);
        assertThat(repoCategory).isNotNull();
    }
}