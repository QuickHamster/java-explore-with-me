package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.Const;
import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.stats.dto.ViewStats;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient  extends BaseClient {

    @Autowired
    public StatsClient(@Value("${STAT-SERVER-URL}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }



    public void postStats(EndpointHit endpointDto) {
        post("/hit", endpointDto);
    }

    public Long getViews(Long eventId) {
        Map<String, Object> parameters = Map.of(
                "start",
                URLEncoder.encode(LocalDateTime.ofEpochSecond(0L, 0,
                        ZoneOffset.UTC).format(Const.DATE_TIME_FORMATTER)),
                "end", URLEncoder.encode(LocalDateTime.now().format(Const.DATE_TIME_FORMATTER),
                        StandardCharsets.UTF_8),
                "uris", (List.of("/events/" + eventId)),
                "unique", "false"
        );
        ResponseEntity<Object> response = get("/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                parameters);
        List<ViewStats> viewStatsList;
        if (response.hasBody()) {
            viewStatsList = (List<ViewStats>) response.getBody();}
        else viewStatsList = List.of();
        if  (viewStatsList != null && !viewStatsList.isEmpty()) {
            return viewStatsList.get(0).getHits();
        } else return 0L;
    }
}
