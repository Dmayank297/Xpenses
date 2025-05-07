package com.example.xpenses.data

import androidx.room.TypeConverter
import com.example.xpenses.models.Category
import com.example.xpenses.models.TransactionType

class Converters {

    // Converter for TransactionType (Enum)
    @TypeConverter
    fun fromTransactionType(type: TransactionType?): String? {
        return type?.name
    }

    @TypeConverter
    fun toTransactionType(value: String?): TransactionType? {
        return value?.let { TransactionType.valueOf(it) }
    }

    // Converter for Category (Custom Data Class)
    @TypeConverter
    fun fromCategory(category: Category?): String? {
        return category?.let { "${it.name}|${it.icon}" }
    }

    @TypeConverter
    fun toCategory(value: String?): Category? {
        return value?.let {
            val parts = it.split("|")
            if (parts.size == 2) {
                Category(name = parts[0], icon = parts[1].toIntOrNull() ?: 0)
            } else {
                null
            }
        }
    }
}