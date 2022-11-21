package ru.practicum.stats.model;

import ru.practicum.ewm.stats.dto.EndpointHit;

public class StatMapper {
    public static Stat fromEndpointHitDtoToStat(EndpointHit endpointHitDto) {
        return Stat.builder()
                .app(endpointHitDto.getApp())
                .ip(endpointHitDto.getIp())
                .uri(endpointHitDto.getUri())
                .timestamp(endpointHitDto.getTimestamp())
                .build();
    }

    public static EndpointHit toEndpointHitDto(Stat stat) {
        return EndpointHit.builder()
                .id(stat.getId())
                .app(stat.getApp())
                .ip(stat.getIp())
                .uri(stat.getUri())
                .timestamp(stat.getTimestamp())
                .build();
    }
}
