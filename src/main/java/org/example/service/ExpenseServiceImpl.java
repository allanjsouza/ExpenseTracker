package org.example.service;

import org.example.dto.ExpenseFilterParamsDTO;
import org.example.model.Expense;
import org.example.model.User;
import org.example.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    @Override
    public List<Expense> listExpenses(Long userId, ExpenseFilterParamsDTO filters) {
        Stream<Expense> result = expenseRepository.findByUserId(userId).stream();

        if (filters.getCategory() != null) {
            result = result.filter(e -> e.getCategory().equals(filters.getCategory()));
        }

        if (filters.getMonth() != null) {
            result = result.filter(e -> e.getDate().startsWith(filters.getMonth()));
        }

        if (filters.getDate() != null) {
            result = result.filter(e -> e.getDate().equals(filters.getDate()));
        }

        if (filters.getExpenseType() != -1) {
            result = result.filter(e -> e.getExpenseType() == filters.getExpenseType());
        }

        return result.toList();
    }

    @Override
    public Optional<Expense> getExpense(Long userId, @NonNull Long id) {
        return expenseRepository.findByUserIdAndId(userId, id);
    }

    @Override
    public Expense addExpense(Long userId, @NonNull Expense expense) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            expense.setUser(user.get());
            return expenseRepository.save(expense);
        }

        throw new RuntimeException("User not found");
    }

    @Override
    public Optional<Expense> updateExpense(Long userId, @NonNull Long id, Expense expense) {
        Optional<Expense> existingExpense = expenseRepository.findByUserIdAndId(userId, id);
        if (existingExpense.isPresent()) {
            expense.setId(id);
            expense.setUser(existingExpense.get().getUser());
            return Optional.of(expenseRepository.save(expense));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Expense> deleteExpense(Long userId, @NonNull Long id) {
        Optional<Expense> existingExpense = expenseRepository.findByUserIdAndId(userId, id);
        if (existingExpense.isPresent()) {
            expenseRepository.deleteById(id);
        }
        return existingExpense;
    }

    @Override
    public List<String> getExpenseCategories(Long userId) {
        return expenseRepository.findByUserId(userId).stream()
                .map(Expense::getCategory).distinct().toList();
    }
}
