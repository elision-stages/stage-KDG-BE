package eu.elision.marketplace.services.helpers;

import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.web.dtos.CategoryDto;
import eu.elision.marketplace.web.dtos.CategoryMakeDto;

import java.util.ArrayList;
import java.util.List;

public class Mapper
{
    private Mapper()
    {
    }

    public static Category toCategory(CategoryMakeDto categoryMakeDto)
    {
        final Category category = new Category();
        category.setName(categoryMakeDto.name());
        return category;
    }

    public static CategoryDto toCategoryDto(Category category)
    {
        return new CategoryDto(category.getId(), category.getName(), new ArrayList<>());
    }

    public static List<CategoryDto> toCategoryDtoList(List<Category> categories)
    {
        List<CategoryDto> dtoList = new ArrayList<>();

        for (Category category : categories)
        {
            if (!categoryInDtoList(dtoList, category))
            {
                if (category.getSubCategories().isEmpty()) dtoList.add(toCategoryDto(category));
                else
                {

                    List<CategoryDto> sub = toCategoryDtoList(category.getSubCategories());
                    CategoryDto parent = toCategoryDto(category);
                    parent.subcategories().addAll(sub);

                    dtoList.add(parent);
                }
            }
        }
        return dtoList;
    }

    public static boolean categoryInDtoList(List<CategoryDto> list, Category category)
    {
        for (CategoryDto categoryDto : list)
        {
            if (categoryDto.id() == category.getId()) return true;
            if (categoryDto.subcategories().stream().anyMatch(categoryDto1 -> categoryDto1.id() == category.getId()))
                return true;
        }
        return false;
    }
}

