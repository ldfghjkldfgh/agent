package ru.mirea.agency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.mirea.agency.db.model.Post;
import ru.mirea.agency.db.dto.PostForm;
import ru.mirea.agency.db.dto.PropertyForm;
import ru.mirea.agency.db.model.User;
import ru.mirea.agency.db.repository.PlanRepository;
import ru.mirea.agency.db.repository.PropertyTypeRepository;
import ru.mirea.agency.service.impl.GrantService;
import ru.mirea.agency.service.PostService;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
public class PostController {

    private final PropertyTypeRepository propertyTypeRepository;

    private final PlanRepository planRepository;

    private final PostService postService;

    private final GrantService grantService;

    public PostController(PropertyTypeRepository propertyTypeRepository,
                          PlanRepository planRepository,
                          PostService postService,
                          GrantService grantService) {
        this.propertyTypeRepository = propertyTypeRepository;
        this.planRepository = planRepository;
        this.postService = postService;
        this.grantService = grantService;
    }

    @GetMapping("/post/create")
    public String createPostForm(ModelMap model, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("propertyTypes", propertyTypeRepository.findAll());
        model.addAttribute("plans", planRepository.findAll());
        model.addAttribute("propertyForm", new PropertyForm());
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("activePage", "createPost");
        return "createPost";
    }

    @PostMapping("/post/create")
    public String creatingPost(ModelMap model, @AuthenticationPrincipal User user, @Valid PropertyForm form1, Errors errors1,
                               @Valid PostForm form2, Errors errors2) {
        if (errors1.hasErrors() || errors2.hasErrors()) {
            model.addAttribute("propertyTypes", propertyTypeRepository.findAll());
            model.addAttribute("plans", planRepository.findAll());
            return "createPost";
        }
        if (user == null) {
            return "redirect:/login";
        }

        Post post = postService.createPost(form1, form2, user);
        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/posts")
    public String postsList(ModelMap model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/posts/my")
    public RedirectView postsList(RedirectAttributes redir, @AuthenticationPrincipal User user) {
        if (user == null) {
            RedirectView redirectView = new RedirectView("/", true);
            return redirectView;
        }
        RedirectView redirectView = new RedirectView("/?owner=" + user.getId(), true);
        redir.addFlashAttribute("activePage", "postsMy");
        return redirectView;
    }

    @GetMapping("/post/{postId}")
    public String showPost(ModelMap model, @AuthenticationPrincipal User user, @PathVariable Long postId) {
        Optional<Post> post = postService.findById(postId);
        if (post.isEmpty()) {
            return "postNotFound";
        }
        model.addAttribute("post", post.get());
        if (user != null && Objects.equals(post.get().getOwner().getId(), user.getId())) {
            model.addAttribute("postOwner", true);
        } else {
            model.addAttribute("postOwner", false);
        }
        return "post";
    }

    @GetMapping("/post/{postId}/edit")
    public String editPost(ModelMap model, @AuthenticationPrincipal User user, @PathVariable Long postId) {
        if (user == null || !grantService.canUserManagePost(user.getId(), postId)) {
            return "noAccess";
        }
        Optional<Post> post = postService.findById(postId);
        if (post.isEmpty()) {
            return "postNotFound";
        }
        model.addAttribute("post", post.get());
        model.addAttribute("propertyTypes", propertyTypeRepository.findAll());
        model.addAttribute("plans", planRepository.findAll());
        return "editPost";
    }

    @PostMapping("/post/{postId}/edit")
    public String editPost(ModelMap model, @AuthenticationPrincipal User user, @PathVariable Long postId, @Valid Post post, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("propertyTypes", propertyTypeRepository.findAll());
            model.addAttribute("plans", planRepository.findAll());
            return "editPost";
        }
        if (user == null || !grantService.canUserManagePost(user.getId(), postId)) {
            return "noAccess";
        }
        Post updatedPost = postService.updatePost(postId, post);
        if (updatedPost == null) {
            return "redirect:/";
        }
        return "redirect:/post/" + post.getId();
    }

    @PostMapping("/post/{postId}/delete")
    public String deletePost(ModelMap model, @AuthenticationPrincipal User user, @PathVariable Long postId) {
        if (user == null || !grantService.canUserManagePost(user.getId(), postId)) {
            return "noAccess";
        }
        postService.delete(postId);
        return "redirect:/posts";
    }

    @ModelAttribute(value = "activePage")
    public String activePage() {
        return "posts";
    }
}
