package ru.practicum.ewm.compilation.model.dto;

import lombok.*;
import ru.practicum.ewm.event.model.dto.EventShortDto;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CompilationDto {
    private Long id;
    private List<EventShortDto> events = new ArrayList<>();
    private Boolean pinned;
    private String title;
}
