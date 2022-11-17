package ru.mirea.agency.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.agency.db.validation.SameFields;

@SameFields(
        message = "Passwords do not match",
        field = "password",
        fieldMatch = "passwordConfirm"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String passwordConfirm;
}
