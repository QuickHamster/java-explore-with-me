package ru.practicum.stats.service;

import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.util.List;

public interface StatService {

    EndpointHit writeEndpointHit(EndpointHit endpointHitDto);

    List<ViewStats> getViewStats(String start, String end, List<String> uris, Boolean unique);
}
