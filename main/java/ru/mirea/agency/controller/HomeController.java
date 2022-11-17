package ru.mirea.agency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.mirea.agency.db.dto.SearchForm;
import ru.mirea.agency.db.model.Post;
import ru.mirea.agency.db.repository.PlanRepository;
import ru.mirea.agency.db.repository.PropertyTypeRepository;
import ru.mirea.agency.service.PostService;

import java.util.List;

@Slf4j
@Controller
public class HomeController {

    private final PropertyTypeRepository propertyTypeRepository;

    private final PlanRepository planRepository;

    private final PostService postService;

    public HomeController(PropertyTypeRepository propertyTypeRepository, PlanRepository planRepository, PostService postService) {
        this.propertyTypeRepository = propertyTypeRepository;
        this.planRepository = planRepository;
        this.postService = postService;
    }

    @GetMapping("/")
    public String search(Model model, SearchForm searchForm, Errors errors) {
        if (errors.hasErrors()) {
            return "posts";
        }
        List<Post> result = postService.searchThroughFilter(
                searchForm.getText(),
                searchForm.getPriceMin(),
                searchForm.getPriceMax(),
                searchForm.getPledgeMin(),
                searchForm.getPledgeMax(),
                searchForm.getAllowedChildren(),
                searchForm.getAllowedAnimals(),
                searchForm.getPlanId(),
                searchForm.getPropertyTypeId(),
                searchForm.getSortPriceDesc(),
                searchForm.getSortPriceAsc(),
                searchForm.getSortNew(),
                searchForm.getOwner()
                );
        model.addAttribute("posts", result);
        model.addAttribute("propertyTypes", propertyTypeRepository.findAll());
        model.addAttribute("plans", planRepository.findAll());
        model.addAttribute("searchForm", searchForm);
        return "home";
    }

    @ModelAttribute(value = "activePage")
    public String activePage() {
        return "posts";
    }
}
