package ru.practicum.ewm.category.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CategoryDto {
    private long id;
    @NotBlank
    private String name;
}
