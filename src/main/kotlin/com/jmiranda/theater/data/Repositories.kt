package com.jmiranda.theater.data

import com.jmiranda.theater.domain.Booking
import com.jmiranda.theater.domain.Performance
import com.jmiranda.theater.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeatRepository : JpaRepository<Seat, Long>

@Repository
interface PerformanceRepository : JpaRepository<Performance, Long>

@Repository
interface BookingRepository : JpaRepository<Booking, Long>