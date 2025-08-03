package org.lessons.java.platform.ticket.ticket_platform.controller;

import org.lessons.java.platform.ticket.ticket_platform.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operators")
public class OperatorController {
    
    @Autowired
    private OperatorRepository operatorRepository;

    @GetMapping
    public String index (Model model){
        model.addAttribute("operators", operatorRepository.findAll());
        return "operators/index";
    }


}