package ru.practicum.ewm.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.compilation.model.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    List<CompilationDto> getCompilations(Boolean pinned, Pageable page);

    CompilationDto addCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compId);

    CompilationDto findCompilationById(Long compId);

    void pinCompilation(Long compId);

    void unpinCompilation(Long compId);

    void addEventToCompilation(Long compId, Long eventId);

    void deleteEventFromCompilation(Long compId, Long eventId);


}
