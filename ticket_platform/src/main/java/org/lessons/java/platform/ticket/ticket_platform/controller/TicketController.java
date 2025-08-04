package org.lessons.java.platform.ticket.ticket_platform.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.lessons.java.platform.ticket.ticket_platform.model.Category;
import org.lessons.java.platform.ticket.ticket_platform.model.Note;
import org.lessons.java.platform.ticket.ticket_platform.model.Operator;
import org.lessons.java.platform.ticket.ticket_platform.model.Ticket;
import org.lessons.java.platform.ticket.ticket_platform.model.TicketState;
import org.lessons.java.platform.ticket.ticket_platform.repository.CategoryRepository;
import org.lessons.java.platform.ticket.ticket_platform.repository.OperatorRepository;
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
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name = "query", required = false) String query) {

        if (query != null && !query.isEmpty() && !query.isBlank()) {
            model.addAttribute("tickets", ticketRepository.findByTicketTitleContainingIgnoreCase(query));
        } else {
            model.addAttribute("tickets", ticketRepository.findAll());
        }
        return "tickets/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        return "tickets/show";
    }

    @GetMapping("/create")
    public String create(Model model) {

        Ticket ticket = new Ticket();
        ticket.setTicketDate(LocalDate.now());
        ticket.setTicketState(TicketState.DA_FARE); 
        model.addAttribute("ticket", ticket);
        model.addAttribute("operators", operatorRepository.findByIsAvailableTrue());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("ticketStates", TicketState.values());
        return "tickets/create";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model) {

        if (formTicket.getAssignedOperator() == null || formTicket.getAssignedOperator().getOperatorId() == null) {
            bindingResult.rejectValue("assignedOperator.operatorId", "NotNull", "Seleziona un operatore valido.");
        }

        if (formTicket.getCategory() == null || formTicket.getCategory().getCategoryId() == null) {
            bindingResult.rejectValue("category.categoryId", "NotNull", "Seleziona una categoria valida.");
        }

        if (bindingResult.hasErrors()) {
            // --- INIZIO: AGGIUNGI QUESTE RIGHE PER IL DEBUG ---
        System.err.println("------------------------------------");
        System.err.println("Errori di validazione nel form ticket:");
        bindingResult.getAllErrors().forEach(error -> {
            if (error instanceof org.springframework.validation.FieldError) {
                org.springframework.validation.FieldError fieldError = (org.springframework.validation.FieldError) error;
                System.err.println("Campo: " + fieldError.getField() + ", Valore rifiutato: '" + fieldError.getRejectedValue() + "', Messaggio: " + fieldError.getDefaultMessage());
            } else {
                System.err.println("Errore globale: " + error.getDefaultMessage());
            }
        });
        System.err.println("------------------------------------");
        // --- FINE: RIGHE AGGIUNTE PER IL DEBUG ---
            model.addAttribute("operators", operatorRepository.findByIsAvailableTrue());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("ticketStates", TicketState.values());
            return "tickets/create";
        }

        Operator operator = operatorRepository.findById(formTicket.getAssignedOperator().getOperatorId())
                .orElseThrow(() -> new IllegalArgumentException("Operatore non trovato"));

        Category category = categoryRepository.findById(formTicket.getCategory().getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria non trovata"));

        formTicket.setAssignedOperator(operator);
        formTicket.setCategory(category);

        ticketRepository.save(formTicket);
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("operators", operatorRepository.findByIsAvailableTrue());
        return "tickets/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid @PathVariable Integer id, @ModelAttribute("ticket") Ticket formTicket,
            BindingResult bindingResult, Model model) {

                
        if (formTicket.getAssignedOperator() == null || formTicket.getAssignedOperator().getOperatorId() == null) {
            bindingResult.rejectValue("assignedOperator.operatorId", "NotNull", "Seleziona un operatore valido.");
        }
        if (formTicket.getCategory() == null || formTicket.getCategory().getCategoryId() == null) {
            bindingResult.rejectValue("category.categoryId", "NotNull", "Seleziona una categoria valida.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("operators", operatorRepository.findByIsAvailableTrue());
            return "tickets/edit";
        }


        Integer operatorId = formTicket.getAssignedOperator().getOperatorId();
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new IllegalArgumentException("Operatore non trovato"));

        formTicket.setAssignedOperator(operator);

        Integer categoryId = formTicket.getCategory().getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria non trovata"));

        formTicket.setCategory(category);

        ticketRepository.save(formTicket);
        return "redirect:/tickets";
    }

    @PostMapping("/delete/{id}")
    public String delete(@Valid @PathVariable Integer id) {
        ticketRepository.deleteById(id);
        return "redirect:/tickets";
    }

    @GetMapping("/notes/{id}")
    public String note(@PathVariable Integer id, Model model) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Non è presente nessun ticket con id" + id + ", quindi non puoi aggiungere delle note");
        }

        model.addAttribute("ticket", ticketOptional.get());
        Note note = new Note();
        note.setTicket(ticketOptional.get());
        note.setNoteCreationDate(LocalDate.now());
        model.addAttribute("note", note);

        return "notes/create";
    }

}
