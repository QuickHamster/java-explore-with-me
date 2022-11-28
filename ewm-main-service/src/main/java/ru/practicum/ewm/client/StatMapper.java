package ru.practicum.ewm.client;



import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class StatMapper {
    public static Stat fromEndpointHitDtoToStat(EndpointHit endpointHitDto) {
        return Stat.builder()
                .app(endpointHitDto.getApp())
                .ip(endpointHitDto.getIp())
                .uri(endpointHitDto.getUri())
                .timestamp(endpointHitDto.getTimestamp())
                .build();
    }

    public static EndpointHit fromStatToEndpointHitDto(Stat stat) {
        return EndpointHit.builder()
                .id(stat.getId())
                .app(stat.getApp())
                .ip(stat.getIp())
                .uri(stat.getUri())
                .timestamp(stat.getTimestamp())
                .build();
    }

    public static EndpointHit toEndpointHitDto(String endpoint, HttpServletRequest request) {
        return EndpointHit.builder()
                .app(endpoint)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
