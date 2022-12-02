package ru.practicum.ewm.compilation.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewCompilationDto {
    @NotNull
    private List<Long> events;
    @NotNull
    private boolean pinned;
    @NotBlank
    private String title;
}
