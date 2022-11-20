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
public class LocationInputDto {
    /*@NotNull*/
    private Float lat;
    /*@NotNull*/
    private Float lon;
}