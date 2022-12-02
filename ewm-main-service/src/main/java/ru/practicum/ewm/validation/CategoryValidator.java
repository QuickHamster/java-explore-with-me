package ru.practicum.ewm.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repo.CategoryRepository;
import ru.practicum.ewm.exception.AlreadyExistException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    public Category validateCategoryOrThrow(long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Category id = %x not found.", id), "Model not found."));
    }

    public List<Long> validateCategories(List<Long> ids) {

        List<Long> results = new ArrayList<>();

        for (Long id : ids) {
            results.add(validateCategoryOrThrow(id).getId());
        }

        return results;
    }

    public void validateCategoryByNameOrThrow(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new AlreadyExistException("Can't create this category.",
                    String.format("Category %s already exist.", categoryName));
        }
    }
}
