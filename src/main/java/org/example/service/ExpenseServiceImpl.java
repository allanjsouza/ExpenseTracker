package org.example.service;

import org.example.dto.ExpenseFilterParams;
import org.example.model.Expense;
import org.example.util.ExpenseDataLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ExpenseServiceImpl implements ExpenseService{

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
}
