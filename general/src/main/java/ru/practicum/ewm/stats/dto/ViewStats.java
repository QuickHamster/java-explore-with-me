package ru.practicum.ewm.stats.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
