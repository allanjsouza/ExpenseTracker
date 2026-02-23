package org.example.service;

import org.example.dto.ExpenseFilterParams;
import org.example.model.Expense;

import java.util.List;

public interface ExpenseService {
    List<Expense> getExpenses(ExpenseFilterParams filters);
}
