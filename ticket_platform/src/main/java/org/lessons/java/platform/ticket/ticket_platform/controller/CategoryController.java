package org.lessons.java.platform.ticket.ticket_platform.controller;

import org.lessons.java.platform.ticket.ticket_platform.model.Category;
import org.lessons.java.platform.ticket.ticket_platform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index (Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories/index";
    }

    @PostMapping
    public String store(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "categories/index";
        }

        categoryRepository.save(formCategory);

        return "redirect:/categories";
    }
    


}
