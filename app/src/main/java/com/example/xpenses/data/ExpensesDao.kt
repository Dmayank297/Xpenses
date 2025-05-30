package com.example.xpenses.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.xpenses.models.CategoryExpense
import com.example.xpenses.models.DailyExpense
import com.example.xpenses.models.Expenses
import com.example.xpenses.models.MonthlyExpense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<Expenses>>

    @Query("SELECT amount FROM expenses")
    fun getAllAmount(): Flow<Double>

    @Query("SELECT * FROM expenses ORDER BY date DESC LIMIT 10")
    fun getRecentExpenses(): Flow<List<Expenses>>

    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expenses")
    fun getTotalExpenditure(): Flow<Double>

    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expenses WHERE type = 'CREDIT'")
    fun getTotalCredit(): Flow<Double>

    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expenses WHERE type = 'DEBIT'")
    fun getTotalDebit(): Flow<Double>

    @Query("SELECT strftime('%w', date) as dayOfWeek, COALESCE(SUM(amount), 0.0) as total FROM expenses GROUP BY dayOfWeek")
    fun getDailyExpenses(): Flow<List<DailyExpense>>

    @Query("SELECT strftime('%m', date) as month, COALESCE(SUM(amount), 0.0) as total FROM expenses GROUP BY month")
    fun getMonthlyExpenses(): Flow<List<MonthlyExpense>>

    @Query("SELECT SUBSTR(category, 1, INSTR(category, '|') - 1) AS categoryName, COALESCE(SUM(amount), 0.0) as total FROM expenses GROUP BY SUBSTR(category, 1, INSTR(category, '|') - 1)")
    fun getExpensesByCategory(): Flow<List<CategoryExpense>>

    @Insert
    suspend fun insertExpense(expense: Expenses)

    @Update
    suspend fun updateExpense(expense: Expenses)

    @Delete
    suspend fun deleteExpense(expense: Expenses)
}