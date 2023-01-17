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
public class NewCommentDto {
    @NotBlank
    @NotNull
    @Size(min = 2, max = 4096)
    private String text;
}
