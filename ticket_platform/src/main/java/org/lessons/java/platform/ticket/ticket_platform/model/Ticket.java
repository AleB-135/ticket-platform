package org.lessons.java.platform.ticket.ticket_platform.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Lob
    @Size(min = 10, max = 2000, message = "Il ticket deve avere un minimo di 10 caratteri e un massimo di 2000")
    @NotBlank(message = "I dettagli del ticket non possono essere vuoti")
    private String details;

    @NotBlank(message = "Lo stato del ticket non può essere vuoto")
    private String ticketState;

    @NotBlank (message = "L'operatore assegnato dev'essere specificato")
    @Size(min = 3, message = "Il nome dell'operatore assegnato deve avere almeno 3 caratteri")
    private String assignedOperator;

    @PastOrPresent
    @NotNull (message = "La data del ticket non può essere vuota")
    private LocalDate ticketDate;

    @OneToMany (mappedBy = "ticket")
    private List <Note> notes;


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

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTicketState() {
        return this.ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
    }

    public String getAssignedOperator() {
        return this.assignedOperator;
    }

    public void setAssignedOperator(String assignedOperator) {
        this.assignedOperator = assignedOperator;
    }

    public LocalDate getTicketDate() {
        return this.ticketDate;
    }

    public void setTicketDate(LocalDate ticketDate) {
        this.ticketDate = ticketDate;
    }
    

}
