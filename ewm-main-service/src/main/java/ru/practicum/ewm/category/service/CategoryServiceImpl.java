package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryDto;
import ru.practicum.ewm.category.model.dto.CategoryMapper;
import ru.practicum.ewm.category.model.dto.NewCategoryDto;
import ru.practicum.ewm.category.repo.CategoryRepository;
import ru.practicum.ewm.exception.AlreadyExistException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories(Pageable pageable) {
        log.info("Trying get categories: {}.", pageable);
        List<CategoryDto> categoryDtoList = CategoryMapper.toCategoryDtoList(categoryRepository.findAll(pageable));
        log.info("getAllCategories end: categoryDtoList{}", categoryDtoList);
        return categoryDtoList;
    }

    @Override
    public CategoryDto getCategoryById(long id) {
        log.info("Trying get category: {}.", id);
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Category id = %x not found.", id), "Model not found."));
        log.info("Category get successfully: {}.", category);
        return CategoryMapper.fromCategoryToCategoryDto(category);
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        log.info("Trying to add a category: {}.", newCategoryDto);
        if (categoryRepository.existsByName(newCategoryDto.getName())) {
            throw new AlreadyExistException("Can't create this category.",
                    String.format("Category %s already exist.", newCategoryDto.getName()));
        } else {
            Category category = CategoryMapper.fromNewCategoryDtoToCategory(newCategoryDto);
            category = categoryRepository.save(category);
            log.info("Category added successfully: {}.", category);
            return CategoryMapper.fromCategoryToCategoryDto(category);
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        log.info("Trying to update a category: {}.", categoryDto);
        Category category = validationCategory(categoryDto.getId());
        category.setName(categoryDto.getName());
        category = categoryRepository.save(category);
        log.info("Category update successfully: {}.", category);
        return CategoryMapper.fromCategoryToCategoryDto(category);
    }

    @Override
    public long deleteCategory(long id) {
        log.info("Trying to delete a category: {}.", id);
        validationCategory(id);
        categoryRepository.deleteById(id);
        log.info("Category delete successfully: {}.", id);
        return id;
    }

    private Category validationCategory(long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Category id = %x not found.", id), "Model not found."));
        /*if (!categoryRepository.existsById(id)) {
            throw new NotFoundException(String.format("Category id = %x not found.", id), "Model not found.");
        }*/
    }
}
