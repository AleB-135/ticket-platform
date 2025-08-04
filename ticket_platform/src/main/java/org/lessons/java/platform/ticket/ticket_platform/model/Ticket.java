package org.lessons.java.platform.ticket.ticket_platform.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "tickets")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;

    @NotBlank(message = "Il titolo del ticket non può essere vuoto")
    private String ticketTitle;

    @NotBlank(message = "Il nome del creatore del ticket non può essere vuoto")
    private String ticketCreator;

    
    @Size(min = 10, max = 2000, message = "Il ticket deve avere un minimo di 10 caratteri e un massimo di 2000")
    @NotBlank(message = "I dettagli del ticket non possono essere vuoti")
    private String ticketDetails;

    @NotNull(message = "Lo stato del ticket deve essere esplicitato")
    @Enumerated(EnumType.STRING)
    private TicketState ticketState;


    @PastOrPresent
    @NotNull (message = "La data del ticket non può essere vuota")
    private LocalDate ticketDate;

    @OneToMany (mappedBy = "ticket", cascade = {CascadeType.REMOVE})
    private List <Note> notes;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator assignedOperator;


    public Operator getAssignedOperator() {
        return this.assignedOperator;
    }

    public void setAssignedOperator(Operator assignedOperator) {
        this.assignedOperator = assignedOperator;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Note> getNotes() {
        return this.notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Integer getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }
 
    public String getTicketTitle() {
        return this.ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getTicketCreator() {
        return this.ticketCreator;
    }

    public void setTicketCreator(String ticketCreator) {
        this.ticketCreator = ticketCreator;
    }

    public String getTicketDetails() {
        return this.ticketDetails;
    }

    public void setTicketDetails(String ticketDetails) {
        this.ticketDetails = ticketDetails;
    }

    public TicketState getTicketState() {
        return this.ticketState;
    }

    public void setTicketState(TicketState ticketState) {
        this.ticketState = ticketState;
    }

    public LocalDate getTicketDate() {
        return this.ticketDate;
    }

    public void setTicketDate(LocalDate ticketDate) {
        this.ticketDate = ticketDate;
    }
    

}
