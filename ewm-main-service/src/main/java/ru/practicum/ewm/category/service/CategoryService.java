package ru.practicum.ewm.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.category.model.dto.CategoryDto;
import ru.practicum.ewm.category.model.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories(Pageable pageable);

    CategoryDto getCategoryById(long id);

    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(long id);
}
