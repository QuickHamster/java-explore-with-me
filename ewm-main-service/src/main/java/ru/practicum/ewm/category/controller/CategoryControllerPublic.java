package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.model.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryControllerPublic {

    private final CategoryService categoryService;

    @GetMapping()
    public List<CategoryDto> getCategories(
            @RequestParam(value = "from",
                    required = false,
                    defaultValue = "0") @Positive Integer from,
            @RequestParam(value = "size",
                    required = false,
                    defaultValue = "10") @PositiveOrZero Integer size) {
        log.info("Get all category: from {}, size {}.", from, size);
        return categoryService.getCategories(PageRequest.of(from / size, size));
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable @Min(0) Long catId) {
        log.info("Get category: id {}.", catId);
        return categoryService.getCategoryById(catId);
    }
}
