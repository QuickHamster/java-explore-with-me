package ru.practicum.ewm.comment.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)

public class NewCommentDto { /*CommentInputDto*/
    @NotBlank
    @NotNull
    private String text;
}
