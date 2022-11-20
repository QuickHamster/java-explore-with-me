package ru.practicum.ewm.user.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NewUserRequest {
    private String name;
    private String email;
}
