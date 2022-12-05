package ru.practicum.ewm.comment.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class EventCommentCountShortDto {
    private Long id;
    @NotBlank
    @NotNull
    @Size(min = 2, max = 256)
    private String title;
    @NotNull
    private Long countComments;
}
