package ru.mirea.agency.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.mirea.agency.db.dto.RegistrationForm;
import ru.mirea.agency.db.model.User;
import ru.mirea.agency.db.repository.UserRepository;
import ru.mirea.agency.service.UserService;
import ru.mirea.agency.service.impl.GrantService;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class UserController {

    private UserRepository userRepository;

    private final UserService userService;

    private final GrantService grantService;

    public UserController(UserRepository userRepository, GrantService grantService, UserService userService) {
        this.userRepository = userRepository;
        this.grantService = grantService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public String getUser(ModelMap model, @AuthenticationPrincipal User user, @PathVariable Long userId) {
        if (user == null) {
            return "redirect:/";
        }
        if (!grantService.canUserManageUser(user.getId(), userId)) {
            return "noAccess";
        }
        User userTarget = userRepository.findById(userId).get();
        model.addAttribute("regForm", new RegistrationForm(userTarget.getId(),
                userTarget.getName(), userTarget.getEmail(), null, null));
        model.addAttribute("activePage", "myprofile");
        return "user";
    }

    @PostMapping("/user/{userId}")
    public String editUser(ModelMap model,
                           @Valid RegistrationForm regForm, Errors errors, @AuthenticationPrincipal User userActor,
                           @PathVariable Long userId) {
        if (errors.hasErrors()) {
            return "user";
        }
        if (regForm == null || userActor == null || regForm.getId() == null) {
            return "redirect:/";
        }
        if (!grantService.canUserManageUser(userActor.getId(), regForm.getId())) {
            return "noAccess";
        }
        User updatedUser = userService.updateUser(regForm);
        if (updatedUser == null) {
            return "noAccess";
        }
        if (Objects.equals(regForm.getId(), userActor.getId())) {
            model.addAttribute("activePage", "myprofile");
        }
        model.addAttribute("regForm", updatedUser);
        return "user";
    }

    @GetMapping("/user/my")
    public RedirectView myProfile(RedirectAttributes redir, @AuthenticationPrincipal User user) {
        if (user == null) {
            RedirectView redirectView = new RedirectView("/", true);
            return redirectView;
        }
        RedirectView redirectView = new RedirectView("/user/" + user.getId(), true);
        redir.addFlashAttribute("activePage", "myprofile");
        return redirectView;
    }

    @PostMapping("/user/{userId}/delete")
    public String editUser(ModelMap model,
                           @AuthenticationPrincipal User userActor,
                           @PathVariable Long userId) {
        if (userActor == null) {
            return "noAccess";
        }
        if (!grantService.canUserManageUser(userActor.getId(), userId)) {
            return "noAccess";
        }
        userService.deleteUser(userId);
        return "redirect:/";
    }
}
