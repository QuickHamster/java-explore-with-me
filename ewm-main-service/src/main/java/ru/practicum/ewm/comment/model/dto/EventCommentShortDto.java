package ru.practicum.ewm.comment.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class EventCommentShortDto {
    private Long id;
    private String title;
}
