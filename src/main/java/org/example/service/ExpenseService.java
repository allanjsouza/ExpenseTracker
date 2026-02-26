package org.example.service;

import org.example.dto.ExpenseFilterParamsDTO;
import org.example.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getExpenses(ExpenseFilterParamsDTO filters);

    Optional<Expense> getExpense(Long id);

    Expense addExpense(Expense expense);

    Optional<Expense> updateExpense(Long id, Expense expense);

    Optional<Expense> deleteExpense(Long id);

    List<String> getExpenseCategories();
}
