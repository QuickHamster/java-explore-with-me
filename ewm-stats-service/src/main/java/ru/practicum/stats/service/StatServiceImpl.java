package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.Const;
import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.stats.dto.ViewStats;
import ru.practicum.stats.model.Stat;
import ru.practicum.stats.model.StatMapper;
import ru.practicum.stats.repo.StatRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StatServiceImpl implements StatService{

    private final StatRepository statRepository;

    @Override
    public EndpointHit writeEndpointHit(EndpointHit endpointHitDto) {
        log.info("Trying to write a EndpointHit: {}.", endpointHitDto);
        Stat stat = statRepository.save(StatMapper.fromEndpointHitDtoToStat(endpointHitDto));
        log.info("EndpointHit write successfully: {}.", stat);
        return StatMapper.toEndpointHitDto(stat);
    }

    @Override
    public List<ViewStats> getViewStats(String start, String end, List<String> uris, Boolean unique) {
        log.info("Trying get statistics: start {}, end {}, uris {}, unique {}.",
                start, end, uris, unique);
        start = URLDecoder.decode(start, StandardCharsets.UTF_8);
        end = URLDecoder.decode(end, StandardCharsets.UTF_8);
        LocalDateTime startDate = LocalDateTime.parse(start, Const.DATE_TIME_FORMATTER);
        LocalDateTime endDate = LocalDateTime.parse(end, Const.DATE_TIME_FORMATTER);
        List<ViewStats> viewStats;
        if (!uris.isEmpty()) {
            viewStats = (unique ? statRepository.getStatsUniqueIpByTimestampAndUris(startDate, endDate, uris)
                    : statRepository.getStatsByTimestampAndUris(startDate, endDate, uris));
        } else {
            viewStats = (unique ? statRepository.getStatsUniqueIpByTimestamp(startDate, endDate)
                    : statRepository.getStatsAllByTimestamp(startDate, endDate));
        }
        log.info("Statistics get successfully: {}.", viewStats);
        return viewStats;
    }
}
