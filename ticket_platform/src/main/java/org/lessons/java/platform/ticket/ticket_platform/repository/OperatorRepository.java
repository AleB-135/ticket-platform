package org.lessons.java.platform.ticket.ticket_platform.repository;

import java.util.List;

import org.lessons.java.platform.ticket.ticket_platform.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository <Operator, Integer> {
    List<Operator> findByIsAvailableTrue();
}
