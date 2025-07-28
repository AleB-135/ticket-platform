package org.lessons.java.platform.ticket.ticket_platform.repository;

import java.util.List;

import org.lessons.java.platform.ticket.ticket_platform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findByTicketTitleContainingIgnoreCase(String ticketTitle);

    // Trova tutti i ticket assegnati a un determinato operatore
    List<Ticket> findByAssignedOperator(String assignedOperator);

    // Trova tutti i ticket per stato (es. "DA_FARE", "IN_CORSO", ...)
    List<Ticket> findByTicketState(String ticketState);

    // Cerca per operatore e stato
    List<Ticket> findByAssignedOperatorAndTicketState(String assignedOperator, String ticketState);
}
