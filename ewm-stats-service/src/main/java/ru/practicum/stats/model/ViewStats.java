package ru.practicum.stats.model;

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
