package com.example.xpenses.models

data class DailyExpense(
    val dayOfWeek: String, // 0 (Sunday) to 6 (Saturday)
    val total: Double
)

data class MonthlyExpense(
    val month: String, // 01 (Jan) to 12 (Dec)
    val total: Double
)

data class CategoryExpense(
    val categoryName: String,
    val total: Double
)
