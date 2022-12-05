package ru.practicum.ewm.comment.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class EventCommentCountShortDto {
    private Long id;
    private String title;
    private Long countComments;
}
