package com.jmiranda.theater.domain

import javax.persistence.*

@Entity(name = "PERFORMANCES")
data class Performance(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val title: String
) {
    /**
     * Not good practice to put fields that has mappings in the default constructor of a data class.
     * The reason is because the equals() and hashCode() functions pretty  inefficient due to that
     * data items will be include there and the toString() will become very unreadable.
     * So, it must be outside the data constructor and must be lateinit to be initialized by hibernate.
     */
    @OneToMany(mappedBy = "performance")
    lateinit var bookings: List<Booking>
}