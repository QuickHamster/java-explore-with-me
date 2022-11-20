package ru.practicum.ewm.category.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewCategoryDto {
    private String name;
}
