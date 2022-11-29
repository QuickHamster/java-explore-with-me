package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.model.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;
import ru.practicum.ewm.util.Const;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationControllerPublic {
    private final CompilationService compilationService;

    @GetMapping()
    public List<CompilationDto> getCompilations(
            @RequestParam(value = "pinned", required = false, defaultValue = "false") Boolean pinned,
            @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", required = false, defaultValue = Const.SIZE_OF_PAGE) @PositiveOrZero Integer size) {
        log.info("Get compilations: pinned {}, from {}, size {}.", pinned, from, size);
        return compilationService.getCompilations(pinned, PageRequest.of(from / size, size));
    }

    @GetMapping("/{compId}")
    public CompilationDto findCompilationById(@PathVariable @PositiveOrZero Long compId) {
        log.info("Find compilations: compId {}.", compId);
        return compilationService.findCompilationById(compId);
    }
}
