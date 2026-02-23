package org.example.controller;

import org.example.dto.ExpenseFilterParams;
import org.example.model.Expense;
import org.example.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> listExpenses(ExpenseFilterParams filters) {
        System.out.println("Filters: " + filters);
        List<Expense> expenses = expenseService.getExpenses(filters);
        return ResponseEntity.ok(expenses);
    }
}
