package ru.practicum.ewm.location.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
/*@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor*/
public class LocationDto {
    private Long id;
    private Float lat;
    private Float lon;
}