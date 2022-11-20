package ru.practicum.ewm.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatsOutputDto {
    private String app;
    private String uri;
    private Long hits;
}
