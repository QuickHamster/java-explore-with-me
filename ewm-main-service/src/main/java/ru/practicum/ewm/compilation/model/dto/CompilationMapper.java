package ru.practicum.ewm.compilation.model.dto;

import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.model.Event;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.ewm.event.model.dto.EventMapper.toShortDtoList;

public class CompilationMapper {

    public static CompilationDto fromCompilationToCompilationDto(Compilation compilation, StatsClient statsClient) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(toShortDtoList(compilation.getEventSet(), statsClient))
                .build();
    }

    public static Compilation fromNewCompilationDtoToCompilation(NewCompilationDto newCompilationDto,
                                                                 Set<Event> events) {
        return Compilation.builder()
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .eventSet(events)
                .build();
    }

    public static List<CompilationDto> toCompilationDtoList(List<Compilation> compilationList,
                                                            StatsClient statsClient) {
            return compilationList.stream().map((Compilation compilation) ->
                    fromCompilationToCompilationDto(compilation, statsClient)).collect(Collectors.toList());
        }
}
