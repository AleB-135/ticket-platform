package org.lessons.java.platform.ticket.ticket_platform.controller;


import org.lessons.java.platform.ticket.ticket_platform.model.Note;
import org.lessons.java.platform.ticket.ticket_platform.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping ("/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @PostMapping
    public String store(@Valid @ModelAttribute("note") Note noteForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("note", noteForm);
            return "notes/create";
        }

        noteRepository.save(noteForm);

        return "redirect:/tickets";
    }
    
    @PostMapping("/delete/{noteId}")
    public String delete(@PathVariable Integer noteId, Model model){
        
        noteRepository.deleteById(noteId);
        return "redirect:/tickets";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("note", noteRepository.findById(id).get());
        return "notes/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("note") Note noteForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("note", noteForm);
            return "notes/edit";
        }

        noteRepository.save(noteForm);
        return "redirect:/tickets";
    }
}
