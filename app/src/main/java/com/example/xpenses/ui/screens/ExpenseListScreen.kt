package com.example.xpenses.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpenses.models.Expenses
import com.example.xpenses.models.TransactionType
import com.example.xpenses.R
import com.example.xpenses.models.Category
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpensesListViewModel = hiltViewModel()
) {

    val expenses = viewModel.expensesList.collectAsState().value
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "All Expenses")
        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = expenses,
                    key = { expense -> expense.id }
                ) { singleExpense ->
                    ExpensesList(expense = singleExpense)
                }
            }

            FloatingActionButton(
                onClick = { showBottomSheet = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = Color(0xFF008000),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_input_add),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                    containerColor = Color(0xFFF5E8DA)
                ) {
                    AddExpenseForm(
                        onAddExpense = { expense ->
                            scope.launch {
                                viewModel.addExpense(expense)
                                sheetState.hide()
                                showBottomSheet = false
                            }
                        },
                        onCancel = {
                            scope.launch {
                                sheetState.hide()
                                showBottomSheet = false
                            }
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun ExpensesList(expense: Expenses) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = expense.category.icon),
                    contentDescription = expense.category.name,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Unspecified
                )

                Column {
                    Text(
                        text = expense.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = expense.date ?: "No date",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Text(
                text = if (expense.type == TransactionType.DEBIT) {
                    "-$${String.format("%.2f", expense.amount)}"
                } else {
                    "$${String.format("%.2f", expense.amount)}"
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (expense.type == TransactionType.DEBIT) Color.Black else Color.Green
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseForm(
    onAddExpense: (Expenses) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var typeExpanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf<TransactionType?>(null) }

    val categories = listOf(
        Category("Food", android.R.drawable.ic_menu_myplaces),
        Category("Travel", android.R.drawable.ic_menu_directions),
        Category("Shopping", android.R.drawable.ic_menu_slideshow)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add New Expense",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name", color = Color(0xFF8B4513)) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF008000),
                unfocusedBorderColor = Color(0xFF8B4513),
                focusedLabelColor = Color(0xFF008000),
                cursorColor = Color(0xFF008000)
            )
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount", color = Color(0xFF8B4513)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF008000),
                unfocusedBorderColor = Color(0xFF8B4513),
                focusedLabelColor = Color(0xFF008000),
                cursorColor = Color(0xFF008000)
            )
        )

        ExposedDropdownMenuBox(
            expanded = categoryExpanded,
            onExpandedChange = { categoryExpanded = !categoryExpanded }
        ) {
            OutlinedTextField(
                value = selectedCategory?.name ?: "Select Category",
                onValueChange = {},
                readOnly = true,
                label = { Text("Category", color = Color(0xFF8B4513)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF008000),
                    unfocusedBorderColor = Color(0xFF8B4513),
                    focusedLabelColor = Color(0xFF008000),
                    cursorColor = Color(0xFF008000)
                )
            )
            DropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name, color = Color.Black) },
                        onClick = {
                            selectedCategory = category
                            categoryExpanded = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = typeExpanded,
            onExpandedChange = { typeExpanded = !typeExpanded }
        ) {
            OutlinedTextField(
                value = selectedType?.name ?: "Select Type",
                onValueChange = {},
                readOnly = true,
                label = { Text("Type", color = Color(0xFF8B4513)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF008000),
                    unfocusedBorderColor = Color(0xFF8B4513),
                    focusedLabelColor = Color(0xFF008000),
                    cursorColor = Color(0xFF008000)
                )
            )
            DropdownMenu(
                expanded = typeExpanded,
                onDismissRequest = { typeExpanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                TransactionType.entries.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.name, color = Color.Black) },
                        onClick = {
                            selectedType = type
                            typeExpanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B4513),
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if (name.isNotBlank() && amount.isNotBlank() && selectedCategory != null && selectedType != null) {
                        val expense = Expenses(
                            name = name,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            category = selectedCategory!!,
                            type = selectedType!!,
                            date = "2025-05-08"
                        )
                        onAddExpense(expense)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF008000),
                    contentColor = Color.White
                ),
                modifier = Modifier.weight(1f),
                enabled = name.isNotBlank() && amount.isNotBlank() && selectedCategory != null && selectedType != null
            ) {
                Text("Add")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ExpensesList(
        Expenses(
            name = "Grocies",
            amount = 240.00,
            date = "2025-23-4",
            category = Category("Groceries", R.drawable.groceries),
            type = TransactionType.DEBIT

        )
    )
}