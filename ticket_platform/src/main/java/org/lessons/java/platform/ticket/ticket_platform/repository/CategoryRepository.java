package org.lessons.java.platform.ticket.ticket_platform.repository;

import org.lessons.java.platform.ticket.ticket_platform.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Integer> {

}
