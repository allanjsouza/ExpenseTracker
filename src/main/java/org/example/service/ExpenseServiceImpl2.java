package org.example.service;

import org.example.dto.ExpenseFilterParams;
import org.example.model.Expense;
import org.example.repository.ExpenseRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ExpenseServiceImpl2 implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl2(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getExpenses(ExpenseFilterParams filters) {
        Stream<Expense> result = expenseRepository.findAll().stream();

        if(filters.getCategory() != null) {
            result = result.filter(e -> e.getCategory().equals(filters.getCategory()));
        }

        if(filters.getMonth() != null) {
            result = result.filter(e -> e.getDate().startsWith(filters.getMonth()));
        }

        if(filters.getDate() != null) {
            result = result.filter(e -> e.getDate().equals(filters.getDate()));
        }

        if(filters.getExpenseType() != -1) {
            result = result.filter(e -> e.getExpenseType() == filters.getExpenseType());
        }

        return result.toList();
    }

    @Override
    public Optional<Expense> getExpense(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Optional<Expense> updateExpense(Long id, Expense expense) {
        if (expenseRepository.existsById(id)) {
            expense.setId(id);
            expenseRepository.save(expense);
            return Optional.of(expense);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Expense> deleteExpense(Long id) {
        Optional<Expense> existingExpense = expenseRepository.findById(id);
        if (existingExpense.isPresent()) {
            expenseRepository.deleteById(id);
        }
        return existingExpense;
    }

    @Override
    public List<String> getExpenseCategories() {
        return expenseRepository.findAll().stream().map(Expense::getCategory).distinct().toList();
    }
}
