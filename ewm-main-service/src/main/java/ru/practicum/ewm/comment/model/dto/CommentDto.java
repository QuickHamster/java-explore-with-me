package ru.practicum.ewm.comment.model.dto;

import lombok.*;
import ru.practicum.ewm.user.model.dto.UserDto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CommentDto {
    private Long id;
    @NotBlank
    @NotNull
    @Size(min = 2, max = 4096)
    private String text;
    @NotNull
    private String createdOn;
    @NotNull
    private UserDto commentator;
    @Enumerated(EnumType.STRING)
    private String status;
    private String updatedOn;
    @NotNull
    private EventCommentShortDto event;
}
