package room_booking.room_booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String surName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;
}