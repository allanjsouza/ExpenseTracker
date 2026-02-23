package org.example.controller;

import org.example.dto.ExpenseFilterParams;
import org.example.model.Expense;
import org.example.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable Long id) {
        Optional<Expense> expense = expenseService.getExpense(id);
        return expense.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense newExpense = expenseService.addExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        Optional<Expense> updatedExpense = expenseService.updateExpense(id, expense);
        return updatedExpense.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable Long id) {
        Optional<Expense> expense = expenseService.deleteExpense(id);
        if (expense.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
