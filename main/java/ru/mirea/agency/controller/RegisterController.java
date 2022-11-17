package ru.mirea.agency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mirea.agency.db.model.User;
import ru.mirea.agency.db.dto.RegistrationForm;
import ru.mirea.agency.db.repository.RoleRepository;
import ru.mirea.agency.db.repository.UserRepository;

import javax.validation.Valid;

@Slf4j
@Controller
public class RegisterController {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegistrationForm form, Errors errors) {
        if (errors.hasErrors()) {
            form.setPassword("");
            form.setPasswordConfirm("");
            return "register";
        }
        User user = new User(form.getEmail(), form.getName(), passwordEncoder.encode(form.getPassword()));
        user.addRole(roleRepository.findById("ROLE_USER").get());
        userRepository.save(user);
        return "redirect:/";
    }

    @ModelAttribute
    public RegistrationForm emptyForm() {
        return new RegistrationForm();
    }

    @ModelAttribute(value = "activePage")
    public String activePage() {
        return "reg";
    }
}
