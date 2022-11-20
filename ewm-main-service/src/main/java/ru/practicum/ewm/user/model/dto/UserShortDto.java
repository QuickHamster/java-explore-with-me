package ru.practicum.ewm.user.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class UserShortDto {
    private long id;
    private String name;
}
