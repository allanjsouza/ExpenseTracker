package org.example.controller;

import org.example.dto.ExpenseFilterParamsDTO;
import org.example.model.Expense;
import org.example.model.User;
import org.example.service.ExpenseService;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> listExpenses(Authentication auth, ExpenseFilterParamsDTO filters) {
        User user = userService.findByUsername(auth.getName());
        List<Expense> expenses = expenseService.listExpenses(user.getId(), filters);

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpense(Authentication auth, @PathVariable Long id) {
        User user = userService.findByUsername(auth.getName());
        Optional<Expense> expense = expenseService.getExpense(user.getId(), id);

        return expense.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(Authentication auth, @RequestBody Expense expense) {
        User user = userService.findByUsername(auth.getName());
        Expense newExpense = expenseService.addExpense(user.getId(), expense);

        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(Authentication auth, @PathVariable Long id,
            @RequestBody Expense expense) {
        User user = userService.findByUsername(auth.getName());
        Optional<Expense> updatedExpense = expenseService.updateExpense(user.getId(), id, expense);

        return updatedExpense.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Expense> deleteExpense(Authentication auth, @PathVariable Long id) {
        User user = userService.findByUsername(auth.getName());
        Optional<Expense> expense = expenseService.deleteExpense(user.getId(), id);

        if (expense.isPresent())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/expense_categories")
    public ResponseEntity<List<String>> listExpenseCategories(Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        List<String> categories = expenseService.getExpenseCategories(user.getId());

        return ResponseEntity.ok(categories);
    }
}
