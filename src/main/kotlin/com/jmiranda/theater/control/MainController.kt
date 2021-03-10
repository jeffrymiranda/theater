package com.jmiranda.theater.control

import com.jmiranda.theater.data.PerformanceRepository
import com.jmiranda.theater.model.CheckAvailabilityBackingBean
import com.jmiranda.theater.service.BookingService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController(
    val bookingService: BookingService,
    val performanceRepository: PerformanceRepository
) {

    /**
     * The RequestMapping value is empty because this is the home path.
     * viewName: templates/seatBooking.html
     * modelName: "bean" is the object in the form. It is also used to reference the {@link CheckAvailabilityBackingBean} attributes.
     */
    @RequestMapping
    fun homePage(): ModelAndView {
        val model = mapOf(
            "bean" to CheckAvailabilityBackingBean(),
            "performances" to this.performanceRepository.findAll(),
            "seatNums" to 1..36,
            "seatRows" to 'A'..'O'
        )
        return ModelAndView("seatBooking", model)
    }

    /**
     * The RequestMapping value is checkAvailability since that is the name for the action of the form in the templates/seatBooking.html.
     */
    @RequestMapping(value = ["checkAvailability"], method = [RequestMethod.POST])
    fun checkAvailability(bean: CheckAvailabilityBackingBean): ModelAndView {
        bean.seat = this.bookingService.findSeat(bean.selectedSeatNum, bean.selectedSeatRow)
        val selectedPerformance = bean.selectedPerformance?.let { this.performanceRepository.findById(it) }
        selectedPerformance?.ifPresent {
            bean.performance = it
            bean.available = this.bookingService.isSeatFree(bean.seat!!, it)
            if (!bean.available!!) {
                bean.booking = this.bookingService.findBooking(bean.seat!!, bean.performance!!)
            }
        }
        val model = mapOf(
            "bean" to bean,
            "performances" to this.performanceRepository.findAll(),
            "seatNums" to 1..36,
            "seatRows" to 'A'..'O'
        )
        return ModelAndView("seatBooking", model)
    }

    @RequestMapping(value = ["booking"], method = [RequestMethod.POST])
    fun booking(bean: CheckAvailabilityBackingBean): ModelAndView {
        val booking = this.bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)
        return ModelAndView("bookingConfirmed", "booking", booking)
    }


    /**
     * This function is to upload the SEAT table with the theater available seats and rows
     * Run it only ONCE.
     */
    /*@RequestMapping(value = ["bootstrap"])
    fun bootstrap(): ModelAndView {
        // create the data and save it to the DB
        val seats = this.theaterService.seats
        this.seatRepository.saveAll(seats)
        return this.homePage()
    }*/
}