package com.jmiranda.theater.model

import com.jmiranda.theater.domain.Booking
import com.jmiranda.theater.domain.Performance
import com.jmiranda.theater.domain.Seat

class CheckAvailabilityBackingBean {
    var selectedSeatNum: Int = 1
    var selectedSeatRow: Char = 'A'
    var selectedPerformance: Long? = null
    var customerName: String = ""

    var available: Boolean? = null
    var seat: Seat? = null
    var performance: Performance? = null
    var booking: Booking? = null
}