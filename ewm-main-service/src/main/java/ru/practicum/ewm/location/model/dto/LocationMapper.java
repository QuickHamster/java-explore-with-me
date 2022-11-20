package ru.practicum.ewm.location.model.dto;


import ru.practicum.ewm.location.model.Location;

public class LocationMapper {
    public static Location toLocation(LocationInputDto locationInputDto) {
        return Location.builder()
                .lat(locationInputDto.getLat())
                .lon(locationInputDto.getLon())
                .build();
    }

    /*public static Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .id(locationDto.getId())
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }*/

    public static LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}