package ru.practicum.ewm.event.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events", schema = "public")
@Builder(toBuilder = true)
@Jacksonized
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 1024)
    private String annotation;

    @NotBlank
    @Column(length = 4096)
    private String description;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    private Boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(length = 256)
    private String title;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "lon", column = @Column(name = "lon")),
            @AttributeOverride(name = "lat", column = @Column(name = "lat"))})
    private Location location;

    private Integer views = 0;
}
