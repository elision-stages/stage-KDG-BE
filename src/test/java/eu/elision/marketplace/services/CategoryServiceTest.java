package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.product.category.attributes.DynamicAttribute;
import eu.elision.marketplace.domain.product.category.attributes.PickList;
import eu.elision.marketplace.domain.product.category.attributes.PickListItem;
import eu.elision.marketplace.domain.product.category.attributes.Type;
import eu.elision.marketplace.repositories.CategoryRepository;
import eu.elision.marketplace.web.dtos.product.CategoryDto;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    Controller controller;
    @Autowired
    CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void findAll() {
        assertThat(categoryService.findAll()).isNotNull();
    }

    @Test
    void testSave() {
        Category category = new Category();

        String name = RandomStringUtils.randomAlphabetic(5);

        category.setName(name);
        long id = categoryService.save(category).getId();

        Category repoCategory = categoryService.findById(id);
        assertThat(repoCategory).isNotNull();
        assertThat(repoCategory.getName()).hasToString(name);
    }

    @Test
    void testSaveWithParent() {
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
    void findByName() {
        Category category = new Category();

        String name = RandomStringUtils.randomAlphabetic(5);

        category.setName(name);
        categoryService.save(category);

        Category repoCategory = categoryService.findByName(name);
        assertThat(repoCategory).isNotNull();
        assertThat(repoCategory.getName()).hasToString(name);
    }

    @Test
    void assertParentNotFound() {
        Category child = new Category();
        final long parentId = RandomUtils.nextLong();

        assertThrows(NotFoundException.class, () -> categoryService.save(child, parentId));
    }

    @Test
    void toCategoryDto() {
        final Category cat1 = new Category();
        final String name = RandomStringUtils.randomAlphabetic(4);
        cat1.setName(name);

        final DynamicAttribute dynamicAttribute = new DynamicAttribute();
        dynamicAttribute.setRequired(RandomUtils.nextBoolean());
        dynamicAttribute.setType(Type.DECIMAL);
        dynamicAttribute.setName(RandomStringUtils.randomAlphabetic(4));
        cat1.getCharacteristics().add(dynamicAttribute);

        final DynamicAttribute dynamicAttribute2 = new DynamicAttribute();
        dynamicAttribute2.setRequired(RandomUtils.nextBoolean());
        dynamicAttribute2.setType(Type.ENUMERATION);
        dynamicAttribute2.setName(RandomStringUtils.randomAlphabetic(4));

        final PickList pickList = new PickList();
        pickList.setItems(new ArrayList<>(List.of(new PickListItem())));
        dynamicAttribute2.setEnumList(pickList);
        cat1.getCharacteristics().add(dynamicAttribute2);

        CategoryDto categoryDto = categoryService.toCategoryDto(cat1);

        assertThat(categoryDto.name()).isEqualTo(name);
    }
}