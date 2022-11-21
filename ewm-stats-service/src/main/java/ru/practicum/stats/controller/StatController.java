package ru.practicum.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.stats.dto.ViewStats;
import ru.practicum.stats.service.StatService;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    public EndpointHit writeEndpointHit(@RequestBody EndpointHit endpointHitDto) {
        log.info("Write EndpointHit: {}.", endpointHitDto);
        return statService.writeEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getViewStats(
            @RequestParam(value = "start") String start,
            @RequestParam(value = "end") String end,
            @RequestParam(value = "uris", defaultValue = "", required = false) List<String> uris,  /*defaultValue = "List.of()"*/
            @RequestParam(value = "unique", defaultValue = "false", required = false) Boolean unique) {
        log.info("Get ViewStats: start {}, end {}, uris {}, unique {}", start, end, uris, unique);
        return statService.getViewStats(start, end, uris, unique);
    }
}
