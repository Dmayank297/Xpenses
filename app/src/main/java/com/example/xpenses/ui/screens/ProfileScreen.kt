package com.example.xpenses.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.xpenses.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section: Profile Picture, Name, and Edit Button
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(ContextCompat.getColor(context, R.color.text_gray)))
                    .border(2.dp, Color(ContextCompat.getColor(context, R.color.accent_orange)), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_my_calendar),
                    contentDescription = "Profile Picture",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "User",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(ContextCompat.getColor(context, R.color.text_black))
            )
            Text(
                text = "Expense Manager Pro",
                fontSize = 14.sp,
                color = Color(ContextCompat.getColor(context, R.color.text_gray))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* TODO: Edit Profile */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(ContextCompat.getColor(context, R.color.accent_orange)),
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_edit),
                    contentDescription = "Edit Profile",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Edit Profile")
            }
        }

        // Stats Section: Total Expenses, Favorite Category, Days Active
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(ContextCompat.getColor(context, R.color.card_background)))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Your Stats",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(ContextCompat.getColor(context, R.color.text_black))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StatItem(
                        icon = android.R.drawable.ic_menu_save,
                        title = "Total Expenses",
                        value = "$1,250",
                        color = R.color.accent_orange
                    )
                    StatItem(
                        icon = R.drawable.favourite,
                        title = "Favorite Category",
                        value = "Food",
                        color = R.color.chart_green
                    )
                    StatItem(
                        icon = android.R.drawable.ic_menu_today,
                        title = "Days Active",
                        value = "45",
                        color = R.color.chart_steel_blue
                    )
                }
            }
        }

        // Achievements Section: Horizontal Scrollable Achievements
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Achievements",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(ContextCompat.getColor(context, R.color.text_black))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AchievementItem(
                    icon = R.drawable.saved,
                    title = "Saved $100",
                    description = "You saved $100 this month!"
                )
                AchievementItem(
                    icon = R.drawable.check,
                    title = "10 Expenses",
                    description = "Tracked 10 expenses!"
                )
                AchievementItem(
                    icon = R.drawable.happiness,
                    title = "Budget Master",
                    description = "Stayed under budget!"
                )
            }
        }

        // Spending Personality Section: Fun Element
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(ContextCompat.getColor(context, R.color.chart_background)))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Spending Personality",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(ContextCompat.getColor(context, R.color.text_black))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "You’re a Foodie Spender! 🍔",
                        fontSize = 14.sp,
                        color = Color(ContextCompat.getColor(context, R.color.text_gray))
                    )
                }
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_myplaces),
                    contentDescription = "Foodie Icon",
                    tint = Color(ContextCompat.getColor(context, R.color.chart_green)),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun StatItem(
    icon: Int,
    title: String,
    value: String,
    color: Int
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = title,
            tint = Color(ContextCompat.getColor(context, color)),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(ContextCompat.getColor(context, R.color.text_black))
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color(ContextCompat.getColor(context, R.color.text_gray))
        )
    }
}

@Composable
fun AchievementItem(
    icon: Int,
    title: String,
    description: String
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.width(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(ContextCompat.getColor(context, R.color.card_background)))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = title,
                tint = Color(ContextCompat.getColor(context, R.color.accent_orange)),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(ContextCompat.getColor(context, R.color.text_black))
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(ContextCompat.getColor(context, R.color.text_gray)),
                maxLines = 2
            )
        }
    }
}

