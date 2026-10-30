package room_booking.room_booking.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length = 50, nullable=false)
    private String name;

    @Column(name="surName", length = 50, nullable=false)
    private String surName;

    @Column(name="email", length = 100, nullable=false)
    private String email;

    @Column(name="createdAt", nullable=false, length = 100)
    private LocalDate createdAt;

    @Column(name="password", nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
