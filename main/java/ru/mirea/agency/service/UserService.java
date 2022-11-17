package ru.mirea.agency.service;

import ru.mirea.agency.db.dto.RegistrationForm;
import ru.mirea.agency.db.model.User;

public interface UserService {
    User updateUser(RegistrationForm regForm);
    void deleteUser(Long userId);
}
