package ru.practicum.ewm.user.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewUserRequest {
    @NotBlank
    private String name;
    @Email
    @NotNull
    private String email;
}
