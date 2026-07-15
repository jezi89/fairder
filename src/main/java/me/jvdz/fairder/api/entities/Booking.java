package me.jvdz.fairder.api.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents one worker's booking for a shift.
 *
 * <p>A shift may have several booking records over time, for example after a
 * cancellation. The rule that a shift can have only one active booking is
 * enforced by {@code BookingService}, which obtains a pessimistic lock on the
 * shift before creating a booking.</p>
 */
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "worker_id", nullable = false)
    private User worker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    /*
     * Snapshot of the rate calculation at the moment of booking.
     * This intentional denormalisation lets the worker verify the agreed rate
     * later, even when the shift's base rate changes afterwards. It supports
     * Fairder's transparent-pricing principle.
     */
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal urgencyFactor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    private LocalDateTime bookedAt;

    /*
     */
    /*
     * Records how this booking entered the option cycle. Only DIRECT_MATCH
     * consumes one open-booking option. A null value represents DIRECT_MATCH
     * for records that existed before this column was introduced.
     *//*

    @Enumerated(EnumType.STRING)
    private BookingOrigin origin;
*/

    protected Booking() {
        // Required by JPA to reconstruct the entity from the database.
    }

    public Booking(
            Shift shift,
            User worker,
            BigDecimal urgencyFactor,
            BigDecimal hourlyRate,
            //TODO: Uncomment when BookingOrigin is implemented
            //            BookingOrigin origin,
            LocalDateTime bookedAt
    ) {
        this.shift = shift;
        this.worker = worker;
        this.urgencyFactor = urgencyFactor;
        this.hourlyRate = hourlyRate;
        this.status = BookingStatus.CONFIRMED;
        //TODO: Uncomment when BookingOrigin is implemented
        //this.origin = origin;
        this.bookedAt = bookedAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public User getWorker() {
        return worker;
    }

    public Shift getShift() {
        return shift;
    }

    public BigDecimal getUrgencyFactor() {
        return urgencyFactor;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public Booking setStatus(BookingStatus status) {
        this.status = status;
        return this;
    }

}