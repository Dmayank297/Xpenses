package com.example.xpenses.models

import androidx.core.content.ContextCompat
import com.example.xpenses.R

object Categories {
    val allCategories = listOf(
        Category("Food", android.R.drawable.ic_menu_myplaces, R.color.chart_green),
        Category("Travel", android.R.drawable.ic_menu_directions, R.color.chart_orange),
        Category("Shopping", android.R.drawable.ic_menu_slideshow, R.color.chart_tomato),
        Category("Entertainment", android.R.drawable.ic_menu_gallery, R.color.chart_steel_blue),
        Category("Bills", android.R.drawable.ic_menu_manage, R.color.chart_gold),
        Category("Health", android.R.drawable.ic_menu_help, R.color.chart_slate_blue),
        Category("Education", android.R.drawable.ic_menu_info_details, R.color.chart_orange_red),
        Category("Miscellaneous", android.R.drawable.ic_menu_more, R.color.chart_light_sea_green)
    )

    fun getCategoryColor(context: android.content.Context, categoryName: String): Int {
        val category = allCategories.find { it.name == categoryName }
        return category?.colorResId?.let { ContextCompat.getColor(context, it) }
            ?: ContextCompat.getColor(context, R.color.chart_dark_orchid)
    }

    fun getCategoryByName(name: String): Category {
        return allCategories.find { it.name == name }
            ?: Category(name, android.R.drawable.ic_menu_help, R.color.chart_dark_orchid)
    }
}