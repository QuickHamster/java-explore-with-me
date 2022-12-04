package ru.practicum.ewm.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "public")
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Email
    @NotBlank
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    //private Boolean commentsBan = false;
    private LocalDateTime banCommentsPeriod;// = LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC);

    public boolean isCommentsBan() {
        return this.banCommentsPeriod.isAfter(LocalDateTime.now());
    }
}
