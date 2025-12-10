package com.epam.rd.autocode.assessment.appliances.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientDto {

    private long id;

    @NotBlank(message = "{user.name.is.mandatory}")
    private String name;
    @NotBlank(message = "{object.client.card}")
    private String card;

    @NotBlank(message = "{user.email.is.blank}")
    @Email(message = "{user.email.is.correctly}")
    private String email;


    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
            message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, and one number"
    )
    private String password;
}
