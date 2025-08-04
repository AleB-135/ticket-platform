package org.lessons.java.platform.ticket.ticket_platform.repository;

import java.util.List;


import org.lessons.java.platform.ticket.ticket_platform.model.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    public List<Ticket> findByTicketTitleContainingIgnoreCase(String ticketTitle);

    List<Ticket> findByAssignedOperator(String assignedOperator);

    
}
