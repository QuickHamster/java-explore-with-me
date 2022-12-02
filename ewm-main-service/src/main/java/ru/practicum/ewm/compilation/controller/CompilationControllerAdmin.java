package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.model.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
@Slf4j
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationControllerAdmin {

    private final CompilationService compilationService;

    @PostMapping()
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        log.info("Add compilation: compilationDto {}.", compilationDto);
        return compilationService.addCompilation(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable @PositiveOrZero Long compId) {
        log.info("Delete compilation: compId {}.", compId);
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable @PositiveOrZero Long compId) {
        log.info("Pin compilation: compId {}.", compId);
        compilationService.pinCompilation(compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable @PositiveOrZero Long compId) {
        log.info("Unpin compilation: compId {}.", compId);
        compilationService.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable @PositiveOrZero Long compId,
                                      @PathVariable @PositiveOrZero Long eventId) {
        log.info("Add event to compilation: compId {}, eventId {}.", compId, eventId);
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable @PositiveOrZero Long compId,
                                           @PathVariable @PositiveOrZero Long eventId) {
        log.info("Delete event from compilation: compId {}, eventId {}.", compId, eventId);
        compilationService.deleteEventFromCompilation(compId, eventId);
    }
}
