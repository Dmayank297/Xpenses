package com.example.xpenses.models

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TransactionType {
    CREDIT, DEBIT
}

data class Category(
    val name: String,
    val icon: Int,
    val colorResId: Int? = null
)


@Entity(tableName = "expenses")
data class Expenses(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val amount: Double,
    val category: Category,
    val type: TransactionType,
    val date: String?
)