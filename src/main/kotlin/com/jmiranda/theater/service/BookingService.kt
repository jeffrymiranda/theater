package com.jmiranda.theater.service

import com.jmiranda.theater.data.BookingRepository
import com.jmiranda.theater.data.SeatRepository
import com.jmiranda.theater.domain.Booking
import com.jmiranda.theater.domain.Performance
import com.jmiranda.theater.domain.Seat
import org.springframework.stereotype.Service


@Service
class BookingService(
    val bookingRepository: BookingRepository,
    val seatRepository: SeatRepository
) {

    fun isSeatFree(seat: Seat, performance: Performance): Boolean {
        return this.bookingRepository.findAll()
            .stream()
            .noneMatch { it.seat == seat && it.performance == performance }
    }

    fun findSeat(seatNum: Int, seatRow: Char): Seat? {
        return this.seatRepository.findAll()
            .firstOrNull { it.num == seatNum && it.row == seatRow }
    }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking {
        val booking = Booking(0, customerName)
        booking.seat = seat
        booking.performance = performance
        return this.bookingRepository.save(booking)
    }

    fun findBooking(seat: Seat, performance: Performance): Booking? {
        return this.bookingRepository.findAll()
            .firstOrNull { it.seat == seat && it.performance == performance }
    }
}