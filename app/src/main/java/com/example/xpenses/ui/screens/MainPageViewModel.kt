package com.example.xpenses.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.xpenses.Screen
import com.example.xpenses.XpensesViewModel
import com.example.xpenses.data.ExpensesDao
import com.example.xpenses.models.Expenses
import com.example.xpenses.models.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val expenseDatabase: ExpensesDao
) : XpensesViewModel() {

    private val _currentBottomScreen: MutableState<Screen.BottomNavScreen> = mutableStateOf(Screen.BottomNavScreen.MainPage)
    val currentBottomScreen: MutableState<Screen.BottomNavScreen> = _currentBottomScreen

    private val _expensesList = MutableStateFlow<List<Expenses>>(emptyList())
    val expensesList: StateFlow<List<Expenses>> = _expensesList.asStateFlow()

    private val _recentExpenses = MutableStateFlow<List<Expenses>>(emptyList())
    val recentExpenses: StateFlow<List<Expenses>> = _recentExpenses.asStateFlow()

    val totalExpenditure: StateFlow<Double> = expenseDatabase.getTotalExpenditure().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    val totalCredit: StateFlow<Double> = expenseDatabase.getTotalCredit().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    val totalDebit: StateFlow<Double> = expenseDatabase.getTotalDebit().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    init {
        viewModelScope.launch {
            expenseDatabase.getAllExpenses().collectLatest { expenses ->
                _expensesList.value = expenses
            }
        }
        viewModelScope.launch {
            expenseDatabase.getRecentExpenses().collectLatest { recent ->
                _recentExpenses.value = recent
            }
        }
    }

    fun onBottomNavItemClick(screen: Screen.BottomNavScreen) {
        _currentBottomScreen.value = screen
    }
}