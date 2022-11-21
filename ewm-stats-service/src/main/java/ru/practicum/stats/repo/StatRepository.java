package ru.practicum.stats.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.stats.dto.ViewStats;
import ru.practicum.stats.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Stat, Long> {
    @Query("select new ru.practicum.ewm.stats.dto.ViewStats(s.app, s.uri, count(s.ip))" +
            "from Stat as s " +
            "where s.timestamp between :start and :end " +
            "group by s.uri, s.app")
    List<ViewStats> getStatsAllByTimestamp(LocalDateTime start, LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.stats.dto.ViewStats(s.app, s.uri, count(distinct s.ip)) " +
            "from Stat as s " +
            "where s.timestamp between :start and :end " +
            "group by s.uri, s.app")
    List<ViewStats> getStatsUniqueIpByTimestamp(LocalDateTime start, LocalDateTime end);

    @Query(value = "select new ru.practicum.ewm.stats.dto.ViewStats(s.app, s.uri, count(s.ip)) " +
            "from Stat as s " +
            "where s.timestamp between :start and :end " +
            "and s.uri in :uris " +
            "group by s.uri, s.app")
    List<ViewStats> getStatsByTimestampAndUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.ewm.stats.dto.ViewStats(s.app, s.uri, count(distinct s.ip)) " +
            "from Stat as s " +
            "where s.timestamp between :start and :end " +
            "and s.uri in :uris " +
            "group by s.uri, s.app")
    List<ViewStats> getStatsUniqueIpByTimestampAndUris(LocalDateTime start, LocalDateTime end, List<String> uris);
}
