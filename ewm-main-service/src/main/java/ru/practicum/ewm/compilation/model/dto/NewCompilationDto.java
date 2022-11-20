package ru.practicum.ewm.compilation.model.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewCompilationDto {
    private List<Long> events;
    private boolean pinned;
    private String title;
}
