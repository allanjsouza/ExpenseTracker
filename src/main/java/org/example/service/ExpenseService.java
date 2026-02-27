package org.example.service;

import org.example.dto.ExpenseFilterParamsDTO;
import org.example.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    List<Expense> listExpenses(Long userId, ExpenseFilterParamsDTO filters);

    Optional<Expense> getExpense(Long userId, Long id);

    Expense addExpense(Long userId, Expense expense);

    Optional<Expense> updateExpense(Long userId, Long id, Expense expense);

    Optional<Expense> deleteExpense(Long userId, Long id);

    List<String> getExpenseCategories(Long userId);
}
