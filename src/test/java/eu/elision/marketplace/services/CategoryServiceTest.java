package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.category.Category;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryServiceTest
{
    @Autowired
    Controller controller;
    @Autowired
    CategoryService categoryService;

    @Test
    void findAll()
    {
        assertThat(categoryService.findAll()).isNotNull();
    }

    @Test
    void testSave()
    {
        Category category = new Category();

        String name = RandomStringUtils.randomAlphabetic(5);

        category.setName(name);
        long id = categoryService.save(category).getId();

        Category repoCategory = categoryService.findById(id);
        assertThat(repoCategory).isNotNull();
        assertThat(repoCategory.getName()).hasToString(name);
    }

    @Test
    void testSaveWithParent()
    {
        Category parent = new Category();
        Category child = new Category();

        String parentName = RandomStringUtils.randomAlphabetic(5);
        String childName = RandomStringUtils.randomAlphabetic(5);

        parent.setName(parentName);
        child.setName(childName);

        long parentId = categoryService.save(parent).getId();
        categoryService.save(child, parentId);

        Category repoParent = categoryService.findById(parentId);
        assertThat(repoParent).isNotNull();
        assertThat(repoParent.getSubCategories()).hasSize(1);
    }

    @Test
    void findByName()
    {
        Category category = new Category();

        String name = RandomStringUtils.randomAlphabetic(5);

        category.setName(name);
        categoryService.save(category);

        Category repoCategory = categoryService.findByName(name);
        assertThat(repoCategory).isNotNull();
        assertThat(repoCategory.getName()).hasToString(name);
    }
}