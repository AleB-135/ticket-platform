package org.lessons.java.platform.ticket.ticket_platform.controller;



import java.time.LocalDate;
import java.util.Optional;

import org.lessons.java.platform.ticket.ticket_platform.model.Note;
import org.lessons.java.platform.ticket.ticket_platform.model.Ticket;
import org.lessons.java.platform.ticket.ticket_platform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@Controller
@RequestMapping ("/tickets")
public class TicketController {

    
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public String index (Model model, @RequestParam (name = "query", required = false)String query){

        if (query != null && !query.isEmpty() && !query.isBlank()) {
            model.addAttribute("tickets", ticketRepository.findByTicketTitleContainingIgnoreCase(query));
        } else {
             model.addAttribute("tickets", ticketRepository.findAll());
        }

    return "tickets/index";
    }

    @GetMapping ("/{id}")
    public String show(@PathVariable Integer id, Model model){
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        return "tickets/show"; 
    }
    

    @GetMapping ("/create")
    public String create (Model model){
        model.addAttribute("ticket", new Ticket());
        return "tickets/create";
    }
        

    @PostMapping
    public String save(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult){

         if (bindingResult.hasErrors()) {
             return "tickets/create";
        }
        ticketRepository.save(formTicket);
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        return "tickets/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid @PathVariable Integer id, @Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "tickets/edit";
        }

        ticketRepository.save(formTicket);
        return "redirect:/tickets";
    }
    
    @PostMapping ("/delete/{id}")
    public String delete (@Valid @PathVariable Integer id){
        ticketRepository.deleteById(id);
        return "redirect:/tickets";
    }

    @GetMapping("/notes/{id}")
    public String note(@PathVariable Integer id, Model model){
        Optional <Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non è presente nessun ticket con id" + id + ", quindi non puoi aggiungere delle note");
        }

        model.addAttribute("ticket", ticketOptional.get());
        Note note = new Note();
        note.setTicket(ticketOptional.get());
        note.setNoteCreationDate(LocalDate.now());
        model.addAttribute("note", note);

        return "notes/create";
    }
       
}
