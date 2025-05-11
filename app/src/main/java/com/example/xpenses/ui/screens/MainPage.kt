package com.example.xpenses.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xpenses.R
import com.example.xpenses.Screen
import com.example.xpenses.bottomNavItems
import com.example.xpenses.models.Categories
import com.example.xpenses.models.TransactionType
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter


@Composable
fun MainPage(
    viewModel: MainPageViewModel = hiltViewModel()
) {
    val currentScreen = remember { viewModel.currentBottomScreen }


    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                bottomNavItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        BottomAppBar(
                            selected = currentScreen.value.bRoute == item.bRoute,
                            screen = item,
                            onItemClick = {
                                Log.d("Navigation", "Navigating to: ${item.bRoute}")
                                viewModel.onBottomNavItemClick(item)
                            }
                        )
                    }
                }
            }
        },
        containerColor = Color(0xFFF5E8DA)
    ) { innerPadding ->
        when (currentScreen.value) {
            Screen.BottomNavScreen.MainPage -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    HeaderSection()
                    CreditCardSection(viewModel = viewModel)
                    AnalyticsSection()
                    RecentActivitySection(
                        onViewAllClick = {
                            viewModel.onBottomNavItemClick(Screen.BottomNavScreen.ExpensesList)
                        }
                    )

                }
            }
            Screen.BottomNavScreen.ExpensesList -> {
                ExpenseListScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            Screen.BottomNavScreen.Profile -> { ProfileScreen(modifier = Modifier.padding(innerPadding)) }
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(android.R.drawable.ic_menu_rotate),
                contentDescription = null,
                tint = Color(0xFFFFA500),
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = "Good Morning,",
                    fontSize = 16.sp,
                    color = Color(0xFF8B4513)
                )
                Text(
                    text = "User",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )
            }
        }
        Icon(
            painter = painterResource(android.R.drawable.ic_menu_manage),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF8B4513))
                .padding(8.dp)
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CreditCardSection(
    viewModel: MainPageViewModel = hiltViewModel()
) {
    val totalExpenditure = viewModel.totalExpenditure.collectAsState().value
    val totalCredit = viewModel.totalCredit.collectAsState().value
    val totalDebit = viewModel.totalDebit.collectAsState().value

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2F2F2F))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TOTAL TRANSACTION",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Icon(
                    painter = painterResource(R.drawable.groceries),
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = "$${String.format("%.2f", totalExpenditure)}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CREDIT",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "$${String.format("%.2f", totalCredit)}",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                Column {
                    Text(
                        text = "DEBIT",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "$${String.format("%.2f", totalDebit)}",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFA500))
                    )
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF008000))
                    )
                }
            }
        }
    }
}

@Composable
fun AnalyticsSection(
    viewModel: MainPageViewModel = hiltViewModel()
) {
    val expensesByCategory = viewModel.expensesByCategory.collectAsState().value
    val context = LocalContext.current
    val categories = Categories.allCategories
    val labels = categories.map { it.name }
    val data = categories.map { category ->
        expensesByCategory.find { it.categoryName == category.name }?.total ?: 0.0
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(ContextCompat.getColor(context, R.color.card_background)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_info_details),
                        contentDescription = null,
                        tint = Color(ContextCompat.getColor(context, R.color.accent_orange)),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Analytics",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(ContextCompat.getColor(context, R.color.text_black))
                    )
                }
            }
            if (data.all { it == 0.0 }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No expenses",
                        fontSize = 14.sp,
                        color = Color(ContextCompat.getColor(context, R.color.text_gray))
                    )
                }
            } else {
                BarChart(
                    data = data,
                    labels = labels,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
fun BarChart(
    data: List<Double>,
    labels: List<String>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier.background(Color(ContextCompat.getColor(context, R.color.chart_background))),
        factory = { ctx ->
            BarChart(ctx).apply {
                description.isEnabled = false
                setDrawGridBackground(false)
                setDrawBorders(false)

                // X-axis configuration
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    textColor = ContextCompat.getColor(ctx, R.color.text_black)
                    textSize = 10f
                    granularity = 1f
                    labelCount = labels.size
                    valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            return labels.getOrElse(value.toInt()) { "" }.take(3)
                        }
                    }
                }

                // Y-axis configuration
                axisLeft.apply {
                    setDrawGridLines(true)
                    textColor = ContextCompat.getColor(ctx, R.color.text_black)
                    textSize = 10f
                    axisMinimum = 0f
                    valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            return "$${String.format("%.0f", value)}"
                        }
                    }
                }
                axisRight.isEnabled = false

                // Legend
                legend.isEnabled = false
            }
        },
        update = { chart ->
            val entries = data.mapIndexed { index, value ->
                BarEntry(index.toFloat(), value.toFloat())
            }

            val dataSet = BarDataSet(entries, "Expenses").apply {
                colors = Categories.allCategories.map { category ->
                    ContextCompat.getColor(context, category.colorResId ?: R.color.chart_dark_orchid)
                }
                valueTextSize = 10f
                valueTextColor = ContextCompat.getColor(context, R.color.text_black)
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return if (value > 0) "$${String.format("%.0f", value)}" else ""
                    }
                }
            }

            val barData = BarData(dataSet).apply {
                barWidth = 0.1f // Adjust bar width
                setValueTextSize(10f)
            }

            chart.data = barData
            chart.setFitBars(true)
            chart.animateY(1000)
            chart.invalidate()
        }
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun RecentActivitySection(
    onViewAllClick: () -> Unit,
    viewModel: MainPageViewModel = hiltViewModel()
) {
    val recentExpenses = viewModel.recentExpenses.collectAsState().value

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Activity",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "VIEW ALL",
                fontSize = 12.sp,
                color = Color(0xFF008000),
                modifier = Modifier
                    .clickable { onViewAllClick() }
            )
        }
        if (recentExpenses.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No recent expenses",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = recentExpenses,
                    key = { expense -> expense.id }
                ) { expense ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
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
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = expense.category.icon),
                                    contentDescription = null,
                                    tint = Color(0xFF008000),
                                    modifier = Modifier.size(24.dp)
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
                                        fontSize = 12.sp,
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
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))

}

@Composable
fun BottomAppBar(
    selected: Boolean,
    screen: Screen.BottomNavScreen,
    onItemClick: () -> Unit
) {
    val icon = if (selected) screen.bSelectedIcon else screen.bIcon

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(vertical = 20.dp), // Internal padding for better spacing
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = screen.bTitle,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            text = screen.bTitle,
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}