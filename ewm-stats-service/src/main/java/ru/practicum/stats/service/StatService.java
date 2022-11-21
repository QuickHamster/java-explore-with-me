package ru.practicum.stats.service;

import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.stats.dto.ViewStats;

import java.util.List;

public interface StatService {

    EndpointHit writeEndpointHit(EndpointHit endpointHitDto);

    List<ViewStats> getViewStats(String start, String end, List<String> uris, Boolean unique);
}
