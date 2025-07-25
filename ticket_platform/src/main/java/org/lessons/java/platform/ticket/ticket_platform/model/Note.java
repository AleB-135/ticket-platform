package org.lessons.java.platform.ticket.ticket_platform.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class Note {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noteId;

    @NotBlank(message = "Il campo 'autore della nota' non può essere vuoto.")
    @Lob
    private String noteAuthor;

    @PastOrPresent(message = "La data di creazione della nota non può essere nel futuro")
    private LocalDate noteCreationDate;

    @NotBlank(message = "Il testo della nota non può essere vuoto")
    private String noteText;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;


    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    
    public Integer getNoteId() {
        return this.noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }
   

    public String getNoteAuthor() {
        return this.noteAuthor;
    }

    public void setNoteAuthor(String noteAuthor) {
        this.noteAuthor = noteAuthor;
    }

    public LocalDate getNoteCreationDate() {
        return this.noteCreationDate;
    }

    public void setNoteCreationDate(LocalDate noteCreationDate) {
        this.noteCreationDate = noteCreationDate;
    }

    public String getNoteText() {
        return this.noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}