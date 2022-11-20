package ru.practicum.ewm.location.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "locations", schema = "public")
@Builder(toBuilder = true)
/*@Data
@Entity
@Builder(toBuilder = true)
@Table(name = "locations")
@NoArgsConstructor
@AllArgsConstructor*/
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;
    private Float lat;
    private Float lon;
}
