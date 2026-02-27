package org.example.repository;

import java.util.List;
import java.util.Optional;

import org.example.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long userId);

    Optional<Expense> findByUserIdAndId(Long userId, Long id);

}
