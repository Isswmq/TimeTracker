package org.krainet.timetracker.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateEditDto {

    @NotBlank(message = "Email must not be blank.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Username must not be blank.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    private String username;

    @NotBlank(message = "Password must not be blank.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String rawPassword;
}