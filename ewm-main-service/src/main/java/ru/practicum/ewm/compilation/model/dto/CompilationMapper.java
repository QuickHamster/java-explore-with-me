package ru.practicum.ewm.compilation.model.dto;

import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.model.Event;

import java.util.List;
import java.util.Set;

public class CompilationMapper {
    public static Compilation fromNewCompilationDtoToCompilation(NewCompilationDto newCompilationDto,
                                                                Set<Event> events) {
        return Compilation.builder()
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .eventSet(events)
                .build();
    }

    public static CompilationDto fromCompilationToCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(toShortDtoList(compilation.getEventSet()))
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static List<CompilationDto> toCompilationDtoList() {
        return null;
    }
}
