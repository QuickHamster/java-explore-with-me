package ru.practicum.ewm.comment.model.dto;

import lombok.*;
import ru.practicum.ewm.user.model.dto.UserDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CommentDto {
    private Long id;
    private String text;
    private String createdOn;
    private UserDto commentator;
    private String status;
    private String updatedOn;
    private EventCommentShortDto event;
}
