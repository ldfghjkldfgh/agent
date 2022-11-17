package ru.mirea.agency.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mirea.agency.db.dto.RegistrationForm;
import ru.mirea.agency.db.model.Role;
import ru.mirea.agency.db.model.User;
import ru.mirea.agency.db.repository.UserRepository;
import ru.mirea.agency.service.UserService;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User updateUser(RegistrationForm regForm) {
        Optional<User> userOpt= userRepository.findById(regForm.getId());
        if (userOpt.isEmpty()) {
            return null;
        }
        User previousUser = userOpt.get();
        String email = (regForm.getEmail() == null) ? previousUser.getEmail() : regForm.getEmail();
        String name = (regForm.getName() == null) ? previousUser.getName() : regForm.getName();
        String password = (regForm.getPassword() == null || regForm.getPassword().equals("")) ? previousUser.getPassword() : passwordEncoder.encode(regForm.getPassword());
        User updatedUser = new User(regForm.getId(), email, name, password);
        for (Role role : previousUser.getRoles()) {
            updatedUser.addRole(role);
        }
        userRepository.save(updatedUser);
        return updatedUser;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
