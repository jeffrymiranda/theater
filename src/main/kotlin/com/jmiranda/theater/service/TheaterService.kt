package com.jmiranda.theater.service

import com.jmiranda.theater.domain.Seat
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TheaterService {

    private val hiddenSeats = mutableListOf<Seat>()

    constructor() {

        fun getPrice(row: Int, num: Int): BigDecimal {
            return when {
                row >= 14 -> BigDecimal(14.50)
                num <= 3 || num >= 34 -> BigDecimal(16.50)
                row == 1 -> BigDecimal(21)
                else -> BigDecimal(18)
            }

        }

        fun getDescription(row: Int, num: Int): String {
            return when {
                row == 15 -> "Back Row"
                row == 14 -> "Cheaper Seat"
                num <= 3 || num >= 34 -> "Restricted View"
                row <= 2 -> "Best View"
                else -> "Standard Seat"
            }
        }

        for (row in 1..15) {
            for (num in 1..36) {
                // The Id can be any number and Hibernate will take care to increase each time new record is persisted.
                // (row + 64).toChar() will result in a uppercase letter. For example: 1 + 64 = 'A' and so on...
                hiddenSeats.add(Seat(0, (row + 64).toChar(), num, getPrice(row, num), getDescription(row, num)))
            }
        }
    }

    val seats
        get() = hiddenSeats.toList()

    fun find(setNum: Int, seatRow: Char): Seat =
        this.seats.first { it.num == setNum && it.row == seatRow }
}
