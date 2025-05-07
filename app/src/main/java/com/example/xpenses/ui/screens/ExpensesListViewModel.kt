package com.example.xpenses.ui.screens

import androidx.lifecycle.viewModelScope
import com.example.xpenses.XpensesViewModel
import com.example.xpenses.data.ExpensesDao
import com.example.xpenses.models.Expenses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesListViewModel @Inject constructor(
    private val expenseDatabase: ExpensesDao
) : XpensesViewModel() {

    private val _expensesList = MutableStateFlow<List<Expenses>>(emptyList())
    val expensesList: StateFlow<List<Expenses>> = _expensesList.asStateFlow()

   init {
       viewModelScope.launch {
           expenseDatabase.getAllExpenses().collectLatest { expenses ->
               _expensesList.value = expenses
           }
       }
   }

    fun addExpense(item: Expenses) {
        viewModelScope.launch{
            expenseDatabase.insertExpense(item)
            }
        }
}