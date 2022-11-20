package ru.practicum.ewm.category.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CategoryDto {
    private long id;
    private String name;
}
