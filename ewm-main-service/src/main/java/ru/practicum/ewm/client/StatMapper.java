package ru.practicum.ewm.client;



import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class StatMapper {
    public static EndpointHit toEndpointHitDto(String endpoint, HttpServletRequest request) {
        return EndpointHit.builder()
                .app(endpoint)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
