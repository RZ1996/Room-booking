package room_booking.room_booking.entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class User {

    private Long id;
    private String name;
    private String surName;
    private String email;
    private LocalDate createdAt;

    enum Role{
        ADMIN,
        USER
    }
}
