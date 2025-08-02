package com.example.alcoolougasolina.util

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {
    @TypeConverter
    fun fromBigDecimalToString(bigDec: BigDecimal): String {
        return bigDec.toString()
    }

    @TypeConverter
    fun fromStrToBigDecimal(bigDecString: String): BigDecimal {
        return BigDecimal(bigDecString)
    }
}