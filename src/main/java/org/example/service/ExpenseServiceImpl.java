package org.example.service;

import org.example.dto.ExpenseFilterParams;
import org.example.model.Expense;
import org.example.util.ExpenseDataLoader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Service
@Profile("json-file")
public class ExpenseServiceImpl implements ExpenseService{
    private static final AtomicLong idCounter = new AtomicLong();

    @Override
    public List<Expense> getExpenses(ExpenseFilterParams filters) {
        Stream<Expense> result = ExpenseDataLoader.getExpenses().stream();

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
        return getExpenseById(id);
    }

    @Override
    public Expense addExpense(Expense expense) {
        expense.setId(idCounter.incrementAndGet());
        ExpenseDataLoader.getExpenses().add(expense);
        return expense;
    }

    @Override
    public Optional<Expense> updateExpense(Long id, Expense expense) {
        Optional<Expense> existingExpense = getExpenseById(id);
        if(existingExpense.isPresent()) {
            expense.setId(existingExpense.get().getId());
            ExpenseDataLoader.getExpenses().remove(existingExpense.get());
            ExpenseDataLoader.getExpenses().add(expense);
            return Optional.of(expense);
        }
        return existingExpense;
    }

    @Override
    public Optional<Expense> deleteExpense(Long id) {
        Optional<Expense> existingExpense = getExpenseById(id);
        existingExpense.ifPresent(expense -> ExpenseDataLoader.getExpenses().remove(expense));
        return existingExpense;
    }

    @Override
    public List<String> getExpenseCategories() {
        return ExpenseDataLoader.getExpenses().stream().map(Expense::getCategory).distinct().toList();
    }

    private Optional<Expense> getExpenseById(Long id) {
        return ExpenseDataLoader.getExpenses().stream().filter(e -> e.getId() == id).findFirst();
    }
}
