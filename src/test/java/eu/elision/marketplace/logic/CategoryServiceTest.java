package eu.elision.marketplace.logic;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.logic.services.product.CategoryService;
import eu.elision.marketplace.repositories.CategoryRepository;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTest
{
    @InjectMocks
    CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void findAll()
    {
        final ArrayList<Category> allCategories = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(allCategories);

        assertThat(categoryService.findAll()).isNotNull();
        assertThat(categoryService.findAll()).isEqualTo(allCategories);
    }

    @Test
    void testSave()
    {
        Category category = new Category();
        category.setId(RandomUtils.nextLong());
        category.setName(RandomStringUtils.randomAlphabetic(5));

        when(categoryRepository.save(category)).thenReturn(category);
        long id = categoryService.save(category).getId();

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        Category repoCategory = categoryService.findById(id);
        assertThat(repoCategory).isNotNull();
        assertThat(repoCategory.getName()).hasToString(category.getName());
    }

    @Test
    void testSaveWithParent()
    {
        Category parent = new Category();
        parent.setId(RandomUtils.nextLong(1L, 100L));
        parent.setName(RandomStringUtils.randomAlphabetic(5));
        Category child = new Category();
        child.setId(RandomUtils.nextLong(100L, 200L));
        child.setName(RandomStringUtils.randomAlphabetic(5));
        child.setParent(parent);

        parent.getSubCategories().add(child);
        when(categoryRepository.save(parent)).thenReturn(parent);
        when(categoryRepository.save(child)).thenReturn(child);

        when(categoryRepository.findById(parent.getId())).thenReturn(Optional.of(parent));
        when(categoryRepository.findById(child.getId())).thenReturn(Optional.of(child));

        long parentId = categoryService.save(parent).getId();
        final CategoryMakeDto childCategoryDto = new CategoryMakeDto(child.getName(), parentId, new ArrayList<>());
        categoryService.save(childCategoryDto);


        Category repoParent = categoryService.findById(parentId);
        assertThat(repoParent).isNotNull();
        assertThat(repoParent.getSubCategories()).hasSize(1);
        assertThat(parent.getSubCategories().stream().findFirst().orElse(null)).isEqualTo(child);

        Category repoChild = categoryService.findById(child.getId());
        assertThat(repoChild).isNotNull();
    }

    @Test
    void findByName()
    {
        Category category = new Category();

        String name = RandomStringUtils.randomAlphabetic(5);

        category.setName(name);

        when(categoryRepository.findCategoryByName(category.getName())).thenReturn(category);

        Category repoCategory = categoryService.findByName(name);
        assertThat(repoCategory).isNotNull();
        assertThat(repoCategory.getName()).hasToString(name);
    }

    @Test
    void assertParentNotFound()
    {
        final long parentId = RandomUtils.nextLong();
        when(categoryRepository.findById(parentId)).thenReturn(Optional.empty());

        final CategoryMakeDto categoryMakeDto = new CategoryMakeDto(RandomStringUtils.randomAlphabetic(10), parentId, new ArrayList<>());
        Exception exception = assertThrows(NotFoundException.class, () -> categoryService.save(categoryMakeDto));

        assertThat(exception.getMessage()).isEqualTo(String.format("Category with id %s not found", parentId));
    }
}