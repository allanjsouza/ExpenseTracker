package org.example.dto;

import lombok.Data;

@Data
public class ExpenseFilterParams {
    private String category;
    private String month;
    private String date;
    private int expenseType = -1;
}
