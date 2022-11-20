package ru.practicum.ewm.category.model.dto;

import org.springframework.data.domain.Page;
import ru.practicum.ewm.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static Category fromNewCategoryDtoToCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .name(newCategoryDto.getName())
                .build();
    }

    public static CategoryDto fromCategoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static List<CategoryDto> toCategoryDtoListFromCategory(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::fromCategoryToCategoryDto)
                .collect(Collectors.toList());
    }

    public static Category fromCategoryDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

    public static List<CategoryDto> toCategoryDtoList(Page<Category> categories) {
        return categories.stream().map(CategoryMapper::fromCategoryToCategoryDto).collect(Collectors.toList());
    }
}
